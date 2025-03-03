package br.com.starter.application.api.work.dtos;

import br.com.starter.domain.work.WorkStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateWorkDTO {
    private String title;
    private String description;
    private WorkStatus status;
    private Long price;

    // TODO a ver o que mais pode ser modificado
}
