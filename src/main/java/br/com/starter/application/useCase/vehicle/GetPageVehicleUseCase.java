package br.com.starter.application.useCase.vehicle;

import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.user.User;
import br.com.starter.domain.vehicle.VehicleService;
import br.com.starter.domain.garage.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPageVehicleUseCase {

    private final VehicleService vehicleService;
    private final GarageService garageService;

    public Optional<?> handler(Integer page, GetPageRequest request, User user) {
        Garage garage = garageService.getByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário não possui uma oficina registrada"
                ));

        return Optional.of(
                vehicleService.getPageByLicensePlate(
                        request.getQuery(),
                        PageRequest.of(page, request.getSize())
                )
        );
    }
}
