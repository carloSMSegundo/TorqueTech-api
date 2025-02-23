package br.com.starter.application.useCase.stockTransaction;

import br.com.starter.application.api.stockTransaction.dtos.InputStockTransactionRequest;
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
public class InputStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final StockItemService stockItemService;
    private final GarageService garageService;
    private final LocalService localService;
    private final ItemService itemService;

    public Optional<?> handler(
        User user,
        InputStockTransactionRequest request
    ) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        var stockTransaction = new StockTransaction();
        stockTransaction.setType(TransactionType.INPUT);
        stockTransaction.setGarage(garage);
        stockTransaction.setCategory(request.getCategory());
        stockTransaction.setQuantity(request.getQuantity());
        stockTransaction.setOwner(user);
        stockTransaction.setPrice(request.getAcquisitionUnitPrice() * request.getQuantity());
        stockTransaction.setTransactionDate(request.getTransactionAt());

        var stockItem = stockItemService.findByItemAndPrice(
            request.getItemId(),
            garage.getId(),
            request.getLocalId(),
            request.getAcquisitionUnitPrice()
        ).orElse(null);

        if (stockItem == null) {
            var item = itemService.getById(request.getItemId()).orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Item não encontrado!"
                )
            );

            var local = localService.getByIdAndGarageId(request.getLocalId(), garage.getId()).orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Item não encontrado!"
                )
            );

            stockItem = new StockItem();

            stockItem.setGarage(garage);
            stockItem.setItem(item);
            stockItem.setLocal(local);
            stockItem.setAcquisitionPrice(request.getAcquisitionUnitPrice());
            stockItem.setPrice(request.getPrice());
            stockItem.setAcquisitionAt(request.getTransactionAt().toLocalDate());
        }

        stockItem.setQuantity(stockItem.getQuantity() + request.getQuantity());

        stockTransaction.setStockItem(stockItem);

        return Optional.of(stockTransactionService.save(stockTransaction));
    }
}
