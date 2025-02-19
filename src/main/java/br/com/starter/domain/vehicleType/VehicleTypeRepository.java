package br.com.starter.domain.vehicleType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, UUID> {
    Page<VehicleType> findAll(Pageable pageable);

    Page<VehicleType> findByBrandContainingIgnoreCaseAndModelContainingIgnoreCase(String brand, String model, Pageable pageable);

    Optional<VehicleType> findByModelAndBrandAndYear(String model, String brand, String year);

}
