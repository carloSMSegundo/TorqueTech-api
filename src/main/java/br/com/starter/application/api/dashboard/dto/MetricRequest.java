package br.com.starter.application.api.dashboard.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MetricRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
