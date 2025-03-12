package br.com.starter.application.useCase.mechanic;

import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.mechanic.MechanicService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetAllMechanicsUseCase {

    private final GarageService garageService;
    private final MechanicService mechanicService;

    public Optional<?> handler(User user) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        return Optional.of(
            mechanicService.findByGarageId(garage.getId())
        );
    }
}
