package br.com.starter.application.api.work.dtos;

import br.com.starter.application.api.vehicle.dtos.CreateVehicleDTO;
import br.com.starter.application.api.workOrder.dtos.CreateWorkOrderRequestDTO;
import br.com.starter.domain.workOrder.WorkOrder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CreateWorkRequestDTO {
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime expectedAt;
    private Long totalCost;
    private Long price;

    private List<CreateWorkOrderRequestDTO> workOrders;

    private UUID mechanicId;
    private UUID vehicleId;
    private UUID customerId;
}
