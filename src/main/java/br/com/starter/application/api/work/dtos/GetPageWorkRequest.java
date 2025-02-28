package br.com.starter.application.api.work.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
    public class GetPageWorkRequest {
    private Integer size = 10;
    private Set<String> licensePlate;
    private String title;
    private String status;
    private LocalDateTime expectedAt;
    private UUID customerId;
    private UUID mechanicId;
}
