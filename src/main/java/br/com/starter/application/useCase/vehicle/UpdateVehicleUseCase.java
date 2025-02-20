package br.com.starter.application.useCase.vehicle;

import br.com.starter.application.api.vehicle.dtos.UpdateVehicleDTO;
import br.com.starter.domain.vehicle.Vehicle;
import br.com.starter.domain.vehicle.VehicleService;
import br.com.starter.domain.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateVehicleUseCase {
    private final VehicleService vehicleService;
    private final UserService userService;

    @Transactional
    public Optional<Vehicle> handler(UUID vehicleId, UpdateVehicleDTO request) {
        var vehicle = vehicleService.getById(vehicleId).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Veículo não encontrado!"
                )
        );

        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setColor(request.getColor());

        return Optional.of(vehicleService.save(vehicle));
    }
}
