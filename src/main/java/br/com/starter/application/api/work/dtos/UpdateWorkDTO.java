package br.com.starter.application.api.work.dtos;

import br.com.starter.domain.work.WorkStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UpdateWorkDTO {
    private String title;
    private String description;
    private LocalDateTime startAt;
    private WorkStatus status;
    private Long price;
}
