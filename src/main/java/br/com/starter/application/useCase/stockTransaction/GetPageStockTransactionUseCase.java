package br.com.starter.application.useCase.stockTransaction;

import br.com.starter.application.api.stockTransaction.dtos.GetStockTransactionPage;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
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
public class GetPageStockTransactionUseCase {
    private final StockTransactionService stockTransactionService;
    private final GarageService garageService;

    public Optional<?> handler(
        User user,
        GetStockTransactionPage request,
        Integer page
    ) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        var ids = stockTransactionService.getPageFilterIds(garage, request);

        var pageRequest = PageRequest.of(page, request.getSize());

        var content = ids != null
            ? stockTransactionService.getAllByIds(ids, pageRequest)
            : stockTransactionService.getAll(pageRequest);

        return Optional.of(content);
    }
}
