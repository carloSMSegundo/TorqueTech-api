package br.com.starter.domain.transactionItem;

import br.com.starter.domain.stockItem.StockItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction_item")
@Getter
@Setter
public class TransactionItem {
    @Id
    private UUID id = UUID.randomUUID();

    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_item_id", nullable = false)
    private StockItem stockItem;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
