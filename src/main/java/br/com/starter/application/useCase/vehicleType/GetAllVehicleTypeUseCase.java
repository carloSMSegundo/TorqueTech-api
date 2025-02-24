package br.com.starter.application.useCase.vehicleType;

import br.com.starter.domain.vehicleType.VehicleType;
import br.com.starter.domain.vehicleType.VehicleTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetAllVehicleTypeUseCase {

    private final VehicleTypeService vehicleTypeService;

    @Transactional
    public List<VehicleType> handler() {
        return vehicleTypeService.findAll();
    }
}
