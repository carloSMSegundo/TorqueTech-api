package br.com.starter.application.api.dashboard.dto;

import lombok.Data;

@Data
public class DashboardMetricsDTO {
    private int varietyOfProducts;
    private int totalProductsInStock;
    private int totalCustomers;
    private int totalMechanics;
    private int openWorksThisMonth;
    private int completedWorks;
    private double revenueFromCompletedWorks;
    private double pendingRevenueFromOpenWorks;
}
