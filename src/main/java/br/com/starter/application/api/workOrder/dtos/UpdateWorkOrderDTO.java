package br.com.starter.application.api.workOrder.dtos;

import br.com.starter.domain.workOrder.WorkOrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UpdateWorkOrderDTO {
    private UUID id;
    private String title;
    private String description;
    private String note;
    private WorkOrderStatus status;
    private LocalDateTime startAt;
    private LocalDateTime expectedAt;
    private Long cost;
}
