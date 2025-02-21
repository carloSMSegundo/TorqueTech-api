package br.com.starter.application.api.vehicleType.dto;

import br.com.starter.domain.vehicleType.VehicleTypeCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateVehicleTypeDTO {
    @NotBlank(message = "A categoria do veículo não pode ser nula.")
    private String model;
    @NotBlank(message = "A categoria do veículo não pode ser nula.")
    private String brand;
    @NotBlank(message = "A categoria do veículo não pode ser nula.")
    private String year;
    @NotNull(message = "A categoria do veículo não pode ser nula.")
    private VehicleTypeCategory category;
}
