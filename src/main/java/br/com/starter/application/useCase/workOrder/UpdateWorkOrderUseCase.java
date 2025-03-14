package br.com.starter.application.useCase.workOrder;

import br.com.starter.application.api.stockTransaction.dtos.OutputStockTransactionRequest;
import br.com.starter.application.api.workOrder.dtos.UpdateWorkOrderDTO;
import br.com.starter.application.useCase.stockTransaction.CancelStockTransactionUseCase;
import br.com.starter.application.useCase.stockTransaction.OutputStockTransactionUseCase;
import br.com.starter.application.useCase.stockTransaction.UpdateOutStockTransactionUseCase;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.stockTransaction.StockTransaction;
import br.com.starter.domain.stockTransaction.StockTransactionService;
import br.com.starter.domain.stockTransaction.TransactionCategory;
import br.com.starter.domain.user.User;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkService;
import br.com.starter.domain.workOrder.WorkOrder;
import br.com.starter.domain.workOrder.WorkOrderService;
import br.com.starter.domain.workOrder.WorkOrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateWorkOrderUseCase {
    private final WorkService workService;
    private final GarageService garageService;
    private final WorkOrderService workOrderService;
    private final UpdateOutStockTransactionUseCase updateOutStockTransactionUseCase;
    private final StockTransactionService stockTransactionService;
    private final OutputStockTransactionUseCase outputStockTransactionUseCase;
    private final CancelStockTransactionUseCase cancelStockTransactionUseCase;

    @Transactional
    public Optional<WorkOrder> handler(UUID workOrderId, User owner, UpdateWorkOrderDTO request) {
        WorkOrder workOrder = workOrderService.getById(workOrderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ordem de serviço não encontrada!"));

        workOrder.setTitle(request.getTitle());
        workOrder.setCost(request.getCost());
        workOrder.setStatus(request.getStatus());
        workOrder.setStartAt(request.getStartAt());
        workOrder.setExpectedAt(request.getExpectedAt());

        if (request.getDescription() != null)
            workOrder.setDescription(request.getDescription());

        if (request.getNote() != null)
            workOrder.setNote(request.getNote());

        StockTransaction stockTransaction = null;

        if (request.getStockItems() != null && !request.getStockItems().isEmpty()) {
            OutputStockTransactionRequest stockTransactionRequest = new OutputStockTransactionRequest();
            stockTransactionRequest.setItems(request.getStockItems());
            stockTransactionRequest.setTransactionAt(LocalDateTime.now());
            stockTransactionRequest.setCategory(TransactionCategory.WORK_ORDER);

            if (workOrder.getStockTransaction() != null) {
                updateOutStockTransactionUseCase.handler(
                        owner,
                        workOrder.getStockTransaction().getId(),
                        stockTransactionRequest
                );
            } else {
                Optional<?> stockTransactionOptional = outputStockTransactionUseCase.handler(owner, stockTransactionRequest);

                if (stockTransactionOptional.isPresent()) {
                    stockTransaction = (StockTransaction) stockTransactionOptional.get();
                    workOrder.setStockTransaction(stockTransaction);
                } else {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao criar transação de estoque!"
                    );
                }
            }
        }

        if (request.getStatus() == WorkOrderStatus.COMPLETED) {
            workOrder.setConcludedAt(LocalDateTime.now());
        } else if (request.getStatus() == WorkOrderStatus.DELETED) {
            workOrder.setDeletedAt(LocalDateTime.now());
            cancelStockTransactionUseCase.handler(owner, workOrder.getStockTransaction().getId());
        }

        var savedWorkOrder = workOrderService.save(workOrder);

        if (stockTransaction != null) {
            stockTransaction.setWorkOrder(savedWorkOrder);
            stockTransactionService.save(stockTransaction);
        }

        return Optional.of(savedWorkOrder);
    }
}
