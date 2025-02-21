package br.com.starter.application.api.vehicle.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateVehicleDTO {
    @NotNull(message = "A placa do veiculo é obrigatória!")
    private String licensePlate;
    @NotNull(message = "A cor do veículo é obrigatória!")
    private String color;
    @NotNull(message = "O Id do modelo do carro é obrigatório!")
    private UUID vehicleTypeId;
    @NotNull(message = "O Id do cliente é obrigatório!")
    private UUID customerId;
}
