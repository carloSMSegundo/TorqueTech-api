package br.com.starter.domain.dasboard;

import br.com.starter.application.api.dashboard.dto.DashboardMetricsDTO;
import br.com.starter.domain.customer.CustomerRepository;
import br.com.starter.domain.mechanic.MechanicRepository;
import br.com.starter.domain.stockItem.StockItemRepository;
import br.com.starter.domain.work.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final StockItemRepository stockItemRepository;
    private final CustomerRepository customerRepository;
    private final MechanicRepository mechanicRepository;
    private final WorkRepository workRepository;

    public DashboardMetricsDTO getMetricsForGarage(UUID garageId, LocalDateTime startDate, LocalDateTime endDate)
    {
        int varietyOfProducts = stockItemRepository.countDistinctItemsByGarageId(garageId);
        int totalProductsInStock = stockItemRepository.sumQuantityByGarageId(garageId);
        int totalCustomers = customerRepository.countByGarageId(garageId);
        int totalMechanics = mechanicRepository.countByGarageId(garageId);

        int openWorks = workRepository.countOpenWorksBetweenDates(garageId, startDate, endDate);
        int completedWorks = workRepository.countCompletedWorksBetweenDates(garageId, startDate, endDate);

        double revenueFromCompletedWorks = Optional.ofNullable(workRepository.sumCompletedWorksRevenue(garageId, startDate, endDate))
                                                    .orElse(0.0);

        double pendingRevenueFromOpenWorks = Optional.ofNullable(workRepository.sumPendingWorksRevenue(garageId, startDate, endDate))
                .orElse(0.0);

        return new DashboardMetricsDTO(
                varietyOfProducts, totalProductsInStock, totalCustomers, totalMechanics,
                openWorks, completedWorks, revenueFromCompletedWorks, pendingRevenueFromOpenWorks
        );
    }
}
