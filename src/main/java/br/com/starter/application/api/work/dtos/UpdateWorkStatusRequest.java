package br.com.starter.application.api.work.dtos;

import br.com.starter.domain.work.WorkStatus;
import lombok.Data;

@Data
public class UpdateWorkStatusRequest {
    private WorkStatus status;
}
