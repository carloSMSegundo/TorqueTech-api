package br.com.starter.application.useCase.item;

import br.com.starter.application.api.common.GetAllRequest;
import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.item.ItemService;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetAllItemUseCase {
    private final ItemService itemService;
    private final GarageService garageService;

    public Optional<?> handler(
        User user
    ) {
        var garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada"
            )
        );

        return Optional.of(itemService.findAllByGarageAndQuery(
            garage
        ));
    }
}
