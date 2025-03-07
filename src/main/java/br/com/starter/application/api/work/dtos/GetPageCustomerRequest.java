package br.com.starter.application.api.work.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class GetPageCustomerRequest {
    private Integer size = 10;
    private Set<String> licensePlate;
    private String title;
    private String status;
    private LocalDateTime expectedAt;
}
