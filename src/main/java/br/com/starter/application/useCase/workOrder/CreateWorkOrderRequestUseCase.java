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
        Garage garage = garageService.getByUser(owner)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não possui uma oficina"
                ));

        Work work = workService.getByIdAndGarageId(request.getWorkId(), garage.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Usuário não autorizado!"
                ));

        WorkOrder workOrder = new WorkOrder();
        workOrder.setTitle(request.getTitle());
        workOrder.setDescription(request.getDescription());
        workOrder.setNote(request.getNote());
        workOrder.setStartAt(request.getStartAt());
        workOrder.setExpectedAt(request.getExpectedAt());
        workOrder.setCost(request.getCost());
        workOrder.setWork(work);

        work.getOrders().add(workOrder);

        // TODO implementar a lógica envolvendo StockTransation

        work.setExpectedAt(workService.calculateWorkExpectedAt(work.getOrders(), work));
        workService.updateTotalCost(work);

        workService.save(work);
        return Optional.of(workOrderService.save(workOrder));
    }
}
