package br.com.starter.application.useCase.workOrder;

import br.com.starter.application.api.workOrder.dtos.CreateWorkOrderRequestDTO;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkService;
import br.com.starter.domain.workOrder.WorkOrder;
import br.com.starter.domain.workOrder.WorkOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateWorkOrderRequestUseCase {

    private final WorkService workService;
    private final WorkOrderService workOrderService;
    private final GarageService garageService;

    @Transactional
    public Optional<WorkOrder> handler(CreateWorkOrderRequestDTO request, User owner) {

        Work work = workService.getById(request.getWorkId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Work não encontrado!"
                ));

        Garage userGarage = garageService.getByUser(owner).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não possui uma oficina registrada"));


        if (!work.getGarage().getId().equals(userGarage.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não autorizado a criar esta WorkOrder.");
        }

        WorkOrder workOrder = new WorkOrder();
        workOrder.setTitle(request.getTitle());
        workOrder.setDescription(request.getDescription());
        workOrder.setNote(request.getNote());
        workOrder.setStartAt(request.getStartAt());
        workOrder.setExpectedAt(request.getExpectedAt());
        workOrder.setCost(request.getCost());

        // TODO implementar a lógica envolvendo StockTransation

        workOrder.setWork(work);

        WorkOrder savedWorkOrder = workOrderService.save(workOrder);

        updateTotalCost(work);

        workService.save(work);

        return Optional.of(savedWorkOrder);
    }
    // Adicionando metódo para recalcular o total cost quando inserida uma nova WorkOrder
    private void updateTotalCost(Work work) {
        Long totalCost = work.getOrders().stream()
                .mapToLong(WorkOrder::getCost)
                .sum();

        work.setTotalCost(totalCost);
    }
}
