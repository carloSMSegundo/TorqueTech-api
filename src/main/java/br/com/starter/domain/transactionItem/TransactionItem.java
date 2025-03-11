package br.com.starter.domain.transactionItem;

import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.stockItem.StockItem;
import br.com.starter.domain.stockTransaction.StockTransaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private StockTransaction transaction;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime createdAt = LocalDateTime.now();
}
