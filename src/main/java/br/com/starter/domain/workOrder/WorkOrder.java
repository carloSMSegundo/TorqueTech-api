package br.com.starter.domain.workOrder;


import br.com.starter.domain.work.Work;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "work_order")
@Getter
@Setter
public class WorkOrder {
    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "work_id", nullable = false)
    private Work work;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startAt; // dúvida - usar o LocalDateTime.now()???
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime concludedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expectedAt;

    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status; // a ver se comeca com alguma por padrão

    private String title;
    private String description;
    private String note;
    private Long cost;

    // private StockTransaction items; // adicionar depois
}
