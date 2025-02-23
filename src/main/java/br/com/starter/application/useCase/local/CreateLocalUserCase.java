package br.com.starter.application.useCase.local;

import br.com.starter.application.api.local.dtos.CreateLocalStockRequest;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.local.Local;
import br.com.starter.domain.local.LocalService;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateLocalUserCase {
    private final GarageService garageService;
    private final LocalService localService;

    public Optional<?> handler(User user, CreateLocalStockRequest request) {
        var garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada"
            )
        );

        ModelMapper mapper = new ModelMapper();

        var local = mapper.map(request, Local.class);
        local.setGarage(garage);

        return Optional.of(localService.save(local));
    }
}
