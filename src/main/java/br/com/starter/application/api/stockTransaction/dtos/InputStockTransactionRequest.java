package br.com.starter.application.api.stockTransaction.dtos;

import br.com.starter.domain.stockTransaction.TransactionCategory;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class InputStockTransactionRequest {
    private UUID itemId;
    private UUID localId;
    private Long acquisitionUnitPrice; // Valor de compra da unidade
    private Long price; // Valor previsto para a venda do produto
    private Integer quantity;
    private TransactionCategory category;
    private LocalDateTime transactionAt;
}
