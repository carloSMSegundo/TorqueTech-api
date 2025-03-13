package br.com.starter.application.api.workOrder.dtos;

import br.com.starter.domain.workOrder.WorkOrder;
import br.com.starter.domain.workOrder.WorkOrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class WorkOrderDTO {
    private UUID id;
    private String title;
    private String description;
    private String note;
    private Long cost;
    private WorkOrderStatus status;
    private WorkDTO work;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime concludedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expectedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deletedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public WorkOrderDTO(WorkOrder workOrder) {
        this.id = workOrder.getId();
        this.title = workOrder.getTitle();
        this.description = workOrder.getDescription();
        this.note = workOrder.getNote();
        this.cost = workOrder.getCost();
        this.status = workOrder.getStatus();
        this.work = new WorkDTO(workOrder.getWork());
        this.startAt = workOrder.getStartAt();
        this.concludedAt = workOrder.getConcludedAt();
        this.expectedAt = workOrder.getExpectedAt();
        this.deletedAt = workOrder.getDeletedAt();
        this.createdAt = workOrder.getCreatedAt();
    }
}
