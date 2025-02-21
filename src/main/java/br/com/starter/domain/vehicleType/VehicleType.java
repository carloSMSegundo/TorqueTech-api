package br.com.starter.domain.vehicleType;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vehicle_types")
@Getter
@Setter
public class VehicleType {
    @Id
    private UUID id = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private VehicleTypeCategory category;

    private String model;
    private String brand;
    private String year;

}

