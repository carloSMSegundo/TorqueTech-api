package br.com.starter.application.api.vehicleType.dto;

import lombok.Data;

@Data
public class CreateVehicleTypeDTO {
    private String model;
    private String brand;
    private String year;
}
