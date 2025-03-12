package br.com.starter.application.useCase.stockItem;

import br.com.starter.application.api.stockItem.dto.GetPageStockItemRequest;
import br.com.starter.application.api.stockTransaction.dtos.GetPageStockTransactionRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.stockItem.StockItemService;
import br.com.starter.domain.stockTransaction.StockTransactionService;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPageStockItemUseCase {
    private final StockItemService stockItemService;
    private final GarageService garageService;

    public Optional<?> handler(
        User user,
        GetPageStockItemRequest request,
        Integer page
    ) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        var ids = stockItemService.getPageFilterIds(garage, request);

        var pageRequest = PageRequest.of(page, request.getSize());

        var content = ids != null
            ? stockItemService.getAllByIds(
                garage.getId(),
                ids,
                pageRequest
            )
            : stockItemService.findAllByGarageId(
                garage.getId(),
                pageRequest
            );

        return Optional.of(content);
    }
}
