package br.com.starter.application.useCase.work;

import br.com.starter.application.api.work.dtos.UpdateWorkStatusRequest;
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
public class GetWorkUseCase {

    private final WorkService workService;
    private final GarageService garageService;

    @Transactional
    public Optional<Work> handler(User user, UUID workId) {
        Garage garage = garageService.getByUser(user)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O usuário não possui uma oficina registrada"
                )
            );

        var work = workService.getByIdAndGarageId(workId, garage.getId()).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Serviço não encontrado!"
            )
        );

        return Optional.of(work);
    }
}
