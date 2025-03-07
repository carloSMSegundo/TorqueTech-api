package br.com.starter.application.useCase.workOrder;

import br.com.starter.application.api.workOrder.dtos.UpdateWorkOrderDTO;
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
public class UpdateWorkOrderUseCase {
    private final WorkService workService;
    private final GarageService garageService;
    private final WorkOrderService workOrderService;

    @Transactional
    public Optional<WorkOrder> handler(UUID workOrderId, UUID workId, User owner, UpdateWorkOrderDTO request) {

        Garage garage = garageService.getByUser(owner)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não possui uma oficina registrada"));

        Work work = workService.getByIdAndGarageId(workId, garage.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não autorizado"));

        WorkOrder workOrder = workOrderService.getByIdAndWork(workOrderId, work.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "WorkOrder não encontrada!"));

        workOrder.setTitle(request.getTitle());
        workOrder.setDescription(request.getDescription());
        workOrder.setCost(request.getCost());
        workOrder.setStatus(request.getStatus());
        workOrder.setNote(request.getNote());
        workOrder.setStartAt(request.getStartAt());
        workOrder.setExpectedAt(request.getExpectedAt());

        if (request.getStatus() == WorkOrderStatus.COMPLETED) {
            workOrder.setConcludedAt(LocalDateTime.now());
        } else if (request.getStatus() == WorkOrderStatus.DELETED) {
            workOrder.setDeletedAt(LocalDateTime.now());
            work.getOrders().remove(workOrder);
        }

        workService.updateTotalCost(work);
        work.setExpectedAt(workService.calculateWorkExpectedAt(work.getOrders(), work));
        workService.save(work);

        return Optional.of(workOrderService.save(workOrder));
    }
}
