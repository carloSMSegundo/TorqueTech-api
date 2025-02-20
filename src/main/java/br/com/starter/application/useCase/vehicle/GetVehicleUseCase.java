package br.com.starter.application.useCase.vehicle;

import br.com.starter.domain.vehicle.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetVehicleUseCase {
    private final VehicleService vehicleService;

    public Optional<?> handler(UUID vehicleId) {
        return vehicleService.getById(vehicleId);
    }
}
