package br.com.starter.application.useCase.local;

import br.com.starter.application.api.common.GetAllRequest;
import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.local.LocalService;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPageLocalUseCase {
    private final LocalService localService;
    private final GarageService garageService;

    public Optional<?> handler(
        User user,
        Integer page,
        GetPageRequest request
    ) {
        var garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada"
            )
        );

        return Optional.of(localService.findAllByGarageAndQuery(
            garage,
            request.getQuery(),
            PageRequest.of(page, request.getSize())
        ));
    }
}
