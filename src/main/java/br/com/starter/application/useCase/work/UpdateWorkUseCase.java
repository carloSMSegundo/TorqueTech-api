package br.com.starter.application.useCase.work;

import br.com.starter.application.api.work.dtos.UpdateWorkDTO;
import br.com.starter.application.api.workOrder.dtos.UpdateWorkOrderDTO;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkService;
import br.com.starter.domain.work.WorkStatus;
import br.com.starter.domain.workOrder.WorkOrder;
import br.com.starter.domain.workOrder.WorkOrderService;
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
public class UpdateWorkUseCase {

    private final WorkService workService;
    private final GarageService garageService;
    private final WorkOrderService workOrderService;

    @Transactional
    public Optional<Work> handler(UUID workId, User owner, UpdateWorkDTO request) {

        Garage garage = garageService.getByUser(owner)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não possui uma oficina"
                ));

        Work work = workService.getByIdAndGarageId(workId, garage.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Usuário não autorizado!"
                ));


        work.setTitle(request.getTitle());
        work.setStartAt(request.getStartAt());
        work.setExpectedAt(request.getExpectedAt());
        work.setDescription(request.getDescription());
        work.setPrice(request.getPrice());
        work.setStatus(request.getStatus());

        if (request.getWorkOrders() != null) {
            for (UpdateWorkOrderDTO workOrderRequest : request.getWorkOrders()) {
                UUID workOrderId = workOrderRequest.getId();

                WorkOrder workOrder = workOrderService.getByIdAndWork(workOrderId, work.getId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "WorkOrder não encontrada para este Work!"
                        ));

                workOrder.setTitle(workOrderRequest.getTitle());
                workOrder.setDescription(workOrderRequest.getDescription());
                workOrder.setNote(workOrderRequest.getNote());
                workOrder.setStatus(workOrderRequest.getStatus());
                workOrder.setStartAt(workOrderRequest.getStartAt());
                workOrder.setExpectedAt(workOrderRequest.getExpectedAt());
                workOrder.setCost(workOrderRequest.getCost());

                workOrderService.save(workOrder);
            }
        }

        if (request.getStatus() == WorkStatus.COMPLETED) {
            work.setConcludedAt(LocalDateTime.now());
        } else if (request.getStatus() == WorkStatus.CANCELED) {
            work.setCancelledAt(LocalDateTime.now());
        }

        return Optional.of(workService.save(work));
    }
}
