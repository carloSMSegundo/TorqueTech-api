package br.com.starter.application.useCase.vehicle;

import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetAllVehiclesByCustomerUseCase {

    private final VehicleService vehicleService;

    public Optional<?> handler(UUID customerId) {
        return Optional.of(
            vehicleService.findAllByCustomer(customerId)
        );
    }
}
