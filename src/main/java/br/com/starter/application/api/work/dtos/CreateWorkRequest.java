package br.com.starter.application.api.work.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateWorkRequest {
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime expectedAt;
    private Long totalCost;
    private Long price;

    private Long mechanicId;
    private Long ownerId;
    private Long vehicleId;
    private Long customerId;
}
