package br.com.starter.application.api.vehicle.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateVehicleDTO {
    @NotBlank(message = "A placa do veiculo é obrigatória!")
    private String licensePlate;
    private String color;
    private UUID vehicleTypeId;
}
