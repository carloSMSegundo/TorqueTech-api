package br.com.starter.application.useCase.workOrder;

import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkService;
import br.com.starter.domain.workOrder.WorkOrder;
import br.com.starter.domain.workOrder.WorkOrderRepository;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPageWorkOrderUseCase {

    private final WorkOrderRepository workOrderRepository;
    private final WorkService workService;
    private final GarageService garageService;

    public Optional<Page<WorkOrder>> handler(User user, UUID workId, Integer page, GetPageRequest request) {
        Garage garage = garageService.getByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "O usuário não possui uma oficina registrada"
                ));

        Work work = workService.getById(workId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Work não encontrado!"
                ));

        if (!work.getGarage().equals(garage)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não tem acesso a este Work!");
        }

        PageRequest pageRequest = PageRequest.of(page, request.getSize());

        Page<WorkOrder> result = workOrderRepository.findByWorkPage(work, pageRequest);

        return Optional.of(result);
    }
}
