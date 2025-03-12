package br.com.starter.application.useCase.workOrder;

import br.com.starter.application.api.workOrder.dtos.UpdateWorkOrderStatusRequest;
import br.com.starter.application.useCase.stockTransaction.CancelStockTransactionUseCase;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
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
public class UpdateWorkOrderStatusUseCase {
    private final WorkService workService;
    private final WorkOrderService workOrderService;
    private final GarageService garageService;
    private final CancelStockTransactionUseCase cancelStockTransactionUseCase;

    @Transactional
    public Optional<WorkOrder> handler(User user, UUID workId, UUID workOrderId, UpdateWorkOrderStatusRequest request) {

        Garage garage = garageService.getByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "O usuário não possui uma oficina registrada"
                ));

        Work work = workService.getByIdAndGarageId(workId, garage.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Usuário não autorizado"
                ));

        WorkOrder workOrder = workOrderService.getByIdAndWork(workOrderId, work.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Ordem de serviço não encontrada!"
                ));

        workOrder.setStatus(request.getStatus());

        if (request.getStatus() == WorkOrderStatus.COMPLETED) {
            workOrder.setConcludedAt(LocalDateTime.now());
        } else if (request.getStatus() == WorkOrderStatus.DELETED) {
            workOrder.setDeletedAt(LocalDateTime.now());
            cancelStockTransactionUseCase.handler(user, workOrder.getStockTransaction().getId());
        }

        return Optional.of(workOrderService.save(workOrder));
    }
}
