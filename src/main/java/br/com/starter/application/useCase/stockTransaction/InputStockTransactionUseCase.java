package br.com.starter.application.useCase.stockTransaction;

import br.com.starter.application.api.stockTransaction.dtos.InputStockItemDTO;
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
import br.com.starter.domain.transactionItem.TransactionItem;
import br.com.starter.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InputStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final StockItemService stockItemService;
    private final GarageService garageService;
    private final LocalService localService;
    private final ItemService itemService;

    @Transactional
    public Optional<?> handler(
        User user,
        InputStockTransactionRequest request
    ) {
        if(request.getItems().isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Não é possivel realizar uma transação de estoque vazia!"
            );
        }

        Garage garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        if(request.getItems().isEmpty()) {
            throw  new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Não é possivel realizar uma transação de estoque vazia!"
            );
        }

       var stockTransaction = getStockTransaction(user, garage, request);

       if(stockTransaction.getItems() == null)
           stockTransaction.setItems(new ArrayList<>());

        request.getItems().forEach(item -> {
            var stockItem = getStockItem(garage, item, request.getTransactionAt().toLocalDate());
            stockTransaction.getItems().add(stockItem);
        });

        return Optional.of(stockTransactionService.save(stockTransaction));
    }

    public StockTransaction getStockTransaction(
        User user,
        Garage garage,
        InputStockTransactionRequest request
    ) {
        var stockTransaction = new StockTransaction();
        stockTransaction.setType(TransactionType.INPUT);
        stockTransaction.setGarage(garage);
        stockTransaction.setOwner(user);

        stockTransaction.setCategory(request.getCategory());
        stockTransaction.setTransactionDate(request.getTransactionAt());

        var transActionTotalQuantity = request.getItems().stream()
            .map(InputStockItemDTO::getQuantity)
            .reduce(0, Integer::sum);

        var transActionTotalItemsPrice = request.getItems().stream()
            .map(InputStockItemDTO::getPrice)
            .reduce(0L, Long::sum);

        stockTransaction.setQuantity(transActionTotalQuantity);
        stockTransaction.setPrice(transActionTotalItemsPrice * transActionTotalQuantity);

        return stockTransaction;
    }

    public TransactionItem getStockItem (
        Garage garage,
        InputStockItemDTO itemRequest,
        LocalDate acquisitionAt
    ) {
        var transActionItem = new TransactionItem();
        transActionItem.setQuantity(itemRequest.getQuantity());

        var stockItem = stockItemService.findByItemAndPrice(
            itemRequest.getItemId(),
            garage.getId(),
            itemRequest.getLocalId(),
            itemRequest.getAcquisitionUnitPrice()
        ).orElse(null);

        if (stockItem == null) {
            var item = itemService.getById(itemRequest.getItemId(), garage).orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Item não encontrado!"
                )
            );

            var local = localService.getByIdAndGarageId(itemRequest.getLocalId(), garage.getId()).orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Item não encontrado!"
                )
            );

            stockItem = new StockItem();

            stockItem.setGarage(garage);
            stockItem.setItem(item);
            stockItem.setLocal(local);
            stockItem.setAcquisitionPrice(itemRequest.getAcquisitionUnitPrice());
            stockItem.setPrice(itemRequest.getPrice());
            stockItem.setAcquisitionAt(acquisitionAt);
        }

        stockItem.setQuantity(stockItem.getQuantity() + itemRequest.getQuantity());

        stockItem = stockItemService.save(stockItem);

        transActionItem.setStockItem(stockItem);

        return transActionItem;
    }
}
