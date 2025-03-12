package br.com.starter.application.useCase.workOrder;

import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkService;
import br.com.starter.domain.workOrder.WorkOrder;
import br.com.starter.domain.workOrder.WorkOrderRepository;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetListWorkOrderUseCase {
    private final WorkOrderRepository workOrderRepository;
    private final WorkService workService;
    private final GarageService garageService;

    public List<WorkOrder> handler(User user, UUID workId) {
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

        return workOrderRepository.findByWork(work);

    }
}
