package br.com.starter.application.useCase.local;

import br.com.starter.application.api.local.dtos.CreateLocalStockRequest;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.local.LocalService;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateLocalUseCase {
    private final LocalService localService;
    private final GarageService garageService;

    public Optional<?> handler(
        User user,
        UUID localId,
        CreateLocalStockRequest request
    ) {
        var garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada"
            )
        );

        var local = localService.getByIdAndGarageId(localId, garage.getId()).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Item não encontrado!"
            )
        );

        local.setName(request.getName());
        local.setDescription(request.getDescription());

        return Optional.of(localService.save(local));
    }
}
