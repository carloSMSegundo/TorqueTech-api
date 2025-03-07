package br.com.starter.application.useCase.work;

import br.com.starter.application.api.work.dtos.UpdateWorkDTO;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkService;
import br.com.starter.domain.work.WorkStatus;
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
        work.setDescription(request.getDescription());
        work.setPrice(request.getPrice());
        work.setStatus(request.getStatus());

        if (request.getStatus() == WorkStatus.COMPLETED) {
            work.setConcludedAt(LocalDateTime.now());
        } else if (request.getStatus() == WorkStatus.CANCELED) {
            work.setCancelledAt(LocalDateTime.now());
        }

        work.setExpectedAt(workService.calculateWorkExpectedAt(work.getOrders(), work));

        return Optional.of(workService.save(work));
    }
}
