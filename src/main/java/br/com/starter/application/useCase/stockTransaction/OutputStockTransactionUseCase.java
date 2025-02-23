package br.com.starter.application.useCase.stockTransaction;

import br.com.starter.application.api.stockTransaction.dtos.InputStockTransactionRequest;
import br.com.starter.application.api.stockTransaction.dtos.OutputStockTransactionRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.item.ItemService;
import br.com.starter.domain.local.LocalService;
import br.com.starter.domain.stockItem.StockItem;
import br.com.starter.domain.stockItem.StockItemService;
import br.com.starter.domain.stockTransaction.StockTransaction;
import br.com.starter.domain.stockTransaction.StockTransactionService;
import br.com.starter.domain.stockTransaction.TransactionType;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OutputStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final StockItemService stockItemService;
    private final GarageService garageService;

    public Optional<?> handler(
        User user,
        OutputStockTransactionRequest request
    ) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        var stockItem = stockItemService.findById(
            request.getItemId(),
            garage
        ).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Item não encontrado!"
            )
        );

        if(stockItem.getQuantity() < request.getQuantity()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O estoque não possui esta quantidade de items!"
            );
        }

        stockItem.setQuantity(stockItem.getQuantity() - request.getQuantity());

        var stockTransaction = new StockTransaction();
        stockTransaction.setType(TransactionType.OUTPUT);
        stockTransaction.setGarage(garage);
        stockTransaction.setCategory(request.getCategory());
        stockTransaction.setQuantity(request.getQuantity());
        stockTransaction.setPrice(stockItem.getPrice() * request.getQuantity());
        stockTransaction.setOwner(user);
        stockTransaction.setTransactionDate(request.getTransactionAt());

        stockTransaction.setStockItem(stockItem);

        return Optional.of(stockTransactionService.save(stockTransaction));
    }
}
