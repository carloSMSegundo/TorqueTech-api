package br.com.starter.application.api.stockTransaction.dtos;

import br.com.starter.domain.stockTransaction.TransactionCategory;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OutputStockTransactionRequest {
    private UUID itemId;
    private UUID localId;
    private Long stockItemId;
    private Integer quantity;
    private TransactionCategory category;
    private LocalDateTime transactionAt;
}
