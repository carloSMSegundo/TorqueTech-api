package br.com.starter.application.api.stockTransaction.dtos;

import br.com.starter.domain.stockTransaction.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateStockTransactionRequest {
    private TransactionType transactionType;
    private Long ownerId;
    private Long stockItemId;
    private Integer quantity;
    private Long price;
    private LocalDateTime transactionDate;
    private Long workOrderId;
}
