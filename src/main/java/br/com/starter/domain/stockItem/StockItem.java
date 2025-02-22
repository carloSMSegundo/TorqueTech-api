package br.com.starter.domain.stockItem;

import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.item.Item;
import br.com.starter.domain.local.Local;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_item")
@Getter
@Setter
public class StockItem {
    @Id
    private UUID id = UUID.randomUUID();

    private Long acquisitionPrice;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false)
    private Local local;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "garage_id", nullable = false)
    private Garage garage;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDate acquisitionAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
