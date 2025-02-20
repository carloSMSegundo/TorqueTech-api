package br.com.starter.domain.vehicle;

import br.com.starter.domain.vehicleType.VehicleType;
import br.com.starter.domain.vehicleType.VehicleTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;  // Para obter o tipo de veículo

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

    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getById(UUID id) {
        return vehicleRepository.findById(id);
    }

    public Optional<VehicleType> getVehicleTypeById(UUID vehicleTypeId) {
        return vehicleTypeRepository.findById(vehicleTypeId);
    }

}
