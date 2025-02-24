package br.com.starter.domain.vehicleType;

import br.com.starter.domain.vehicle.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleTypeService {

    private final VehicleTypeRepository vehicleTypeRepository;

    public VehicleType save(VehicleType vehicleType) {
        return vehicleTypeRepository.save(vehicleType);
    }

    public Optional<VehicleType> findById(UUID id) {
        return vehicleTypeRepository.findById(id);
    }

    public Optional<VehicleType> getById(UUID id) {
        return vehicleTypeRepository.findById(id);
    }

    public List<VehicleType> findAll(){
        return vehicleTypeRepository.findAll();
    }

    public VehicleType update(UUID id, VehicleType vehicleType) {
        VehicleType existingVehicleType = vehicleTypeRepository.findById(id).orElse(null);

        if (existingVehicleType != null) {
            existingVehicleType.setModel(vehicleType.getModel());
            existingVehicleType.setBrand(vehicleType.getBrand());
            existingVehicleType.setYear(vehicleType.getYear());

            return vehicleTypeRepository.save(existingVehicleType);
        }

        return null;
    }

    public Optional<VehicleType> getVehicleTypeById(UUID vehicleTypeId) {
        return vehicleTypeRepository.findById(vehicleTypeId);
    }

    public Page<VehicleType> listVehicleTypes(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        return vehicleTypeRepository.findAll(pageable);
    }


    public Page<VehicleType> filterByBrandAndModel(String brand, String model, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        return vehicleTypeRepository.findByBrandContainingIgnoreCaseAndModelContainingIgnoreCase(brand, model, pageable);
    }

    public boolean existsByModel(String model) {
        return vehicleTypeRepository.findByModel(model).isPresent();
    }
}
