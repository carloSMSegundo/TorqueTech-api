package br.com.starter.domain.vehicle;

import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.user.UserStatus;
import br.com.starter.domain.vehicleType.VehicleType;
import br.com.starter.domain.vehicleType.VehicleTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;


    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getVehicleType() != null && vehicle.getVehicleType().getId() != null) {
            Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(vehicle.getVehicleType().getId());
            if (vehicleType.isPresent()) {
                vehicle.setVehicleType(vehicleType.get());
            } else {
                throw new RuntimeException("Tipo de veículo não encontrado.");
            }
        }
        return vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> getById(UUID id) {
        return vehicleRepository.findById(id);
    }

    public Optional<VehicleType> getVehicleTypeById(UUID vehicleTypeId) {
        return vehicleTypeRepository.findById(vehicleTypeId);
    }

    public Page<Vehicle> getPageByLicensePlate(
            String licensePlate,
            Pageable pageable
    ) {
        return vehicleRepository.findPageByLicensePlate(
                licensePlate,
                pageable
        );
    }

    public List<Vehicle> findAllByCustomer(UUID customerId) {
        return vehicleRepository.findAllByCustomer(customerId);
    }

    public Page<Vehicle> findByCustomerAndGarage(
            UUID customerId,
            UUID garageId,
            String licensePlate,
            Pageable pageable
    ) {
        return vehicleRepository.findByCustomerAndGarage(customerId, garageId, licensePlate, pageable);
    }



}
