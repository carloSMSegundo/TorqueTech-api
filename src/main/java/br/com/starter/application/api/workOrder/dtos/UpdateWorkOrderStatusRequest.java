package br.com.starter.application.api.workOrder.dtos;

import br.com.starter.domain.workOrder.WorkOrderStatus;
import lombok.Data;

@Data
public class UpdateWorkOrderStatusRequest {
    private WorkOrderStatus status;
}
