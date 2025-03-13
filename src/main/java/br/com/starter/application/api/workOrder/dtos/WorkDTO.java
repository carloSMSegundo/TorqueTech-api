package br.com.starter.application.api.workOrder.dtos;

import br.com.starter.domain.work.Work;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class WorkDTO {
    private UUID id;
    private String title;
    private String description;
    private Long totalCost;
    private Long price;

    public WorkDTO(Work work) {
        this.id = work.getId();
        this.title = work.getTitle();
        this.description = work.getDescription();
        this.totalCost = work.getTotalCost();
        this.price = work.getPrice();
    }
}
