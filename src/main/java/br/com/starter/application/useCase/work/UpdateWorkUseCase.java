package br.com.starter.application.useCase.work;

import br.com.starter.application.api.work.dtos.UpdateWorkDTO;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateWorkUseCase {

    private final WorkService workService;
    private final GarageService garageService;

    @Transactional
    public Optional<Work> handler(UUID workId, User owner, UpdateWorkDTO request) {
        Work work = workService.getById(workId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Work não encontrado!"
                ));

        garageService.validateUserGarage(owner, work);

        work.setTitle(request.getTitle());
        work.setDescription(request.getDescription());
        work.setPrice(request.getPrice());
        work.setStatus(request.getStatus());

        // TODO lógica de datas

        return Optional.of(workService.save(work));
    }
}
