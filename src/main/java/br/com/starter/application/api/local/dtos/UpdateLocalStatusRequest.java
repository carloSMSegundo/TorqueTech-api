package br.com.starter.application.api.local.dtos;

import br.com.starter.domain.local.LocalStatus;
import lombok.Data;

@Data
public class UpdateLocalStatusRequest {
    private LocalStatus status;
}
