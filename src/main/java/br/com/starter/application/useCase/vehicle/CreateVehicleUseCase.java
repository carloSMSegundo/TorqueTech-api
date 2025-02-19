package br.com.starter.application.useCase.vehicle;

import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.vehicle.Vehicle;
import br.com.starter.domain.vehicle.VehicleRepository;
import br.com.starter.domain.vehicleType.VehicleType;
import br.com.starter.domain.vehicleType.VehicleTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateVehicleUseCase {

    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;
    private final VehicleTypeService vehicleTypeService;

    public Vehicle execute(String licensePlate, String color, String model, String brand, String year, UUID customerId) {
        VehicleType vehicleType = vehicleTypeService.findOrCreate(model, brand, year);

        Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(licensePlate);
        vehicle.setColor(color);
        vehicle.setVehicleType(vehicleType);
        vehicle.setCustomer(customer);

        return vehicleRepository.save(vehicle);
    }


}
