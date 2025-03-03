package br.com.starter.application.useCase.workOrder;

import br.com.starter.application.api.work.dtos.UpdateWorkDTO;
import br.com.starter.application.api.workOrder.dtos.UpdateWorkOrderDTO;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkService;
import br.com.starter.domain.workOrder.WorkOrder;
import br.com.starter.domain.workOrder.WorkOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
        Work work = workService.getById(workId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Work não encontrado!"
                ));

        WorkOrder workOrder = workOrderService.getById(workOrderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "WorkOrder não encontrado!"
                ));

        garageService.validateUserGarage(owner, work);

        workOrder.setTitle(request.getTitle());
        workOrder.setDescription(request.getDescription());
        workOrder.setCost(request.getCost());
        workOrder.setStatus(request.getStatus());
        workOrder.setNote(request.getNote());
        workOrder.setStartAt(request.getStartAt());
        workOrder.setExpectedAt(request.getExpectedAt());

        // TODO lógica de datas

        return Optional.of(workOrderService.save(workOrder));
    }
}
