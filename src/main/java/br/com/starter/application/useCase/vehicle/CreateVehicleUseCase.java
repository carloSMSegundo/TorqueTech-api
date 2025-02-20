package br.com.starter.application.useCase.vehicle;

import br.com.starter.application.api.vehicle.dtos.CreateVehicleDTO;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.vehicle.Vehicle;
import br.com.starter.domain.vehicle.VehicleRepository;
import br.com.starter.domain.vehicleType.VehicleType;
import br.com.starter.domain.vehicleType.VehicleTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateVehicleUseCase {

    private final VehicleRepository vehicleRepository;
    private final VehicleTypeService vehicleTypeService;

    @Transactional
    public Vehicle handler(CreateVehicleDTO request) {
        VehicleType vehicleType = vehicleTypeService.findById(request.getVehicleTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de veículo não encontrado"));

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setColor(request.getColor());
        vehicle.setVehicleType(vehicleType);

        return vehicleRepository.save(vehicle);
    }
}
