package br.com.starter.domain.dasboard;

import br.com.starter.application.api.dashboard.dto.DashboardMetricsDTO;
import br.com.starter.application.api.dashboard.dto.MetricRequest;
import br.com.starter.domain.customer.CustomerRepository;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.mechanic.MechanicRepository;
import br.com.starter.domain.stockItem.StockItemRepository;
import br.com.starter.domain.user.User;
import br.com.starter.domain.work.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private final GarageService garageService;

    public DashboardMetricsDTO getMetricsForGarage(User user, MetricRequest request)
    {
        Garage garage = garageService.getByUser(user)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O usuário não possui uma oficina registrada"
            ));

        var startDate = request.getStartDate().atStartOfDay();
        var endDate = request.getEndDate().atTime(23, 59, 59);

        int varietyOfProducts = stockItemRepository.countDistinctItemsByGarageId(garage.getId());
        int totalProductsInStock = stockItemRepository.sumQuantityByGarageId(garage.getId());
        int totalCustomers = customerRepository.countByGarageId(garage.getId());
        int totalMechanics = mechanicRepository.countByGarageId(garage.getId());

        int openWorks = workRepository.countOpenWorksBetweenDates(garage.getId(), startDate, endDate);
        int completedWorks = workRepository.countCompletedWorksBetweenDates(garage.getId(), startDate, endDate);

        double revenueFromCompletedWorks = Optional.ofNullable(
            workRepository.sumCompletedWorksRevenue(garage.getId(), startDate, endDate)
        ).orElse(0.0);

        double pendingRevenueFromOpenWorks = Optional.ofNullable(
            workRepository.sumPendingWorksRevenue(garage.getId(), startDate, endDate)
        ).orElse(0.0);

        var metrics = new DashboardMetricsDTO();
        metrics.setCompletedWorks(completedWorks);
        metrics.setPendingRevenueFromOpenWorks(pendingRevenueFromOpenWorks);
        metrics.setOpenWorksThisMonth(openWorks);
        metrics.setTotalMechanics(totalMechanics);
        metrics.setTotalCustomers(totalCustomers);
        metrics.setTotalProductsInStock(totalProductsInStock);
        metrics.setVarietyOfProducts(varietyOfProducts);
        metrics.setRevenueFromCompletedWorks(revenueFromCompletedWorks);

        return metrics;
    }
}
