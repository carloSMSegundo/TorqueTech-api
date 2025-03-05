package br.com.starter.application.api.stockTransaction.dtos;

import br.com.starter.domain.stockTransaction.TransactionCategory;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OutputStockTransactionRequest {
    private UUID stockItemId;
    private Integer quantity;
    private Long price;
    private LocalDateTime transactionAt;
    private TransactionCategory category;
}
