package br.com.starter.application.api.stockTransaction.dtos;

import br.com.starter.domain.stockTransaction.TransactionType;
import br.com.starter.domain.user.UserStatus;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class GetStockTransactionPage {
    private Integer size = 10;
    private Set<UUID> itemsIs;
    private Set<UUID> ids;
    private UUID ownerId;
    private TransactionType transactionType;
    private String query = "";
}
