package br.com.starter.domain.dasboard;

import br.com.starter.application.api.dashboard.dto.DashboardMetricsDTO;
import br.com.starter.domain.customer.CustomerRepository;
import br.com.starter.domain.mechanic.MechanicRepository;
import br.com.starter.domain.stockItem.StockItemRepository;
import br.com.starter.domain.work.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final StockItemRepository stockItemRepository;
    private final CustomerRepository customerRepository;
    private final MechanicRepository mechanicRepository;
    private final WorkRepository workRepository;

    public DashboardMetricsDTO getMetricsForGarage(UUID garageId)
    {
        int varietyOfProducts = stockItemRepository.countDistinctItemsByGarageId(garageId);
        int totalProductsInStock = stockItemRepository.sumQuantityByGarageId(garageId);
        int totalCustomers = customerRepository.countByGarageId(garageId);
        int totalMechanics = mechanicRepository.countByGarageId(garageId);
        int openWorksThisMonth = workRepository.countOpenWorksThisMonth(garageId, LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        int completedWorks = workRepository.countCompletedWorksByGarageId(garageId);

        return new DashboardMetricsDTO(varietyOfProducts, totalProductsInStock, totalCustomers, totalMechanics, openWorksThisMonth, completedWorks);
    }
}
