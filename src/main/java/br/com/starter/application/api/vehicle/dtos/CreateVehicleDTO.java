package br.com.starter.application.api.vehicle.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateVehicleDTO {
    private String licensePlate;
    private String color;
    private UUID vehicleTypeId;
}
