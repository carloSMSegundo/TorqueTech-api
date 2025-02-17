package br.com.starter.domain.vehicle;

import br.com.starter.domain.vehicleType.VehicleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
public class Vehicle {
    @Id
    private UUID id = UUID.randomUUID();

    private String licensePlate;
    private String color;

    // Relacionamento com a entidade VehicleType
    @ManyToOne(fetch = FetchType.LAZY) // Indica que um veículo pode ter apenas um tipo, mas um tipo pode ser associado a vários veículos.
    @JoinColumn(name = "vehicle_type_id", foreignKey = @ForeignKey(name = "fk_vehicle_vehicle_type"))
    private VehicleType vehicleType;  // Associa o tipo de veículo

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
