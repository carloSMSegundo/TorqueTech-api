package br.com.starter.application.useCase.vehicleType;

import br.com.starter.application.api.vehicleType.dto.CreateVehicleTypeDTO;
import br.com.starter.domain.vehicleType.VehicleType;
import br.com.starter.domain.vehicleType.VehicleTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateVehicleTypeUseCase {

    private final VehicleTypeService vehicleTypeService;

    public VehicleType handler(CreateVehicleTypeDTO request) {
        if (vehicleTypeService.existsByModel(request.getModel())) {
            throw new IllegalArgumentException("Já existe um veículo com esse modelo cadastrado.");
        }

        VehicleType vehicleType = new VehicleType();
        vehicleType.setModel(request.getModel());
        vehicleType.setBrand(request.getBrand());
        vehicleType.setYear(request.getYear());
        vehicleType.setCategory(request.getCategory());

        return vehicleTypeService.save(vehicleType);
    }
}
