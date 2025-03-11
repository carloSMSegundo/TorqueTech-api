package br.com.starter.application.api.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardMetricsDTO {
    private int varietyOfProducts;
    private int totalProductsInStock;
    private int totalCustomers;
    private int totalMechanics;
    private int openWorksThisMonth;
    private int completedWorks;
}
