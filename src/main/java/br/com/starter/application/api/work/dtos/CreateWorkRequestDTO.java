package br.com.starter.application.api.work.dtos;

import br.com.starter.application.api.workOrder.dtos.CreateWorkOrderRequestDTO;
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
    private Long price;

    private List<CreateWorkOrderRequestDTO> workOrders;

    private UUID mechanicId;
    private UUID vehicleId;
    private UUID customerId;
}
