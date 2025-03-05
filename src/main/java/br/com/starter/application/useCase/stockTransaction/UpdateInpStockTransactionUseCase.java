package br.com.starter.application.useCase.stockTransaction;

import br.com.starter.application.api.stockTransaction.dtos.InputStockTransactionRequest;
import br.com.starter.application.api.stockTransaction.dtos.OutputStockTransactionRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.item.ItemService;
import br.com.starter.domain.local.LocalService;
import br.com.starter.domain.stockItem.StockItemService;
import br.com.starter.domain.stockTransaction.StockTransactionService;
import br.com.starter.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateInpStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final StockItemService stockItemService;
    private final GarageService garageService;
    private final LocalService localService;
    private final ItemService itemService;

    @Transactional
    public Optional<?> handler(
        User user,
        UUID stockTransactionId,
        InputStockTransactionRequest request
    ) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        var stockTransaction = stockTransactionService.getByIdAndGarageId(
            stockTransactionId,
            garage.getId()
        ).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Essa movimentação de estoque não pode ser encontrada!"
            )
        );

        var stockItem = stockItemService.findById(
            stockTransaction.getStockItem().getId(),
            garage
        ).orElseThrow();

        // Adiciona a quantidade que foi adicionada anteriormente
        stockItem.setQuantity(stockItem.getQuantity() - stockTransaction.getQuantity());

        if(request.getItemId() != null) {
            var item = itemService.getById(request.getItemId(), garage).orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Item não encontrado!"
                )
            );

            stockItem.setItem(item);
        }

        if(request.getLocalId() != null) {
            var local = localService.getByIdAndGarageId(request.getLocalId(), garage.getId()).orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Item não encontrado!"
                )
            );

            stockItem.setLocal(local);
        }

        stockItem.setAcquisitionPrice(request.getAcquisitionUnitPrice());
        stockItem.setPrice(request.getPrice());
        stockItem.setQuantity(stockItem.getQuantity() + request.getQuantity());

        stockItemService.save(stockItem);

        stockTransaction.setCategory(request.getCategory());
        stockTransaction.setQuantity(request.getQuantity());
        stockTransaction.setPrice(request.getPrice());
        stockTransaction.setTransactionDate(request.getTransactionAt());

        return Optional.of(stockTransactionService.save(stockTransaction));
    }
}
