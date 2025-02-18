package br.com.starter.application.useCase.vehicleType;

import br.com.starter.domain.vehicleType.VehicleType;
import br.com.starter.domain.vehicleType.VehicleTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateVehicleTypeUseCase {

    private final VehicleTypeService vehicleTypeService;

    public VehicleType handler(String model, String brand, String year) {
        VehicleType vehicleType = new VehicleType();
        vehicleType.setModel(model);
        vehicleType.setBrand(brand);
        vehicleType.setYear(year);

        return vehicleTypeService.save(vehicleType);
    }
}
