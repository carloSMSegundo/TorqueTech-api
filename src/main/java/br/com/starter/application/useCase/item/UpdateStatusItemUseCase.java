package br.com.starter.application.useCase.item;

import br.com.starter.application.api.item.dtos.CreateItemRequest;
import br.com.starter.application.api.item.dtos.UpdateItemStatusRequest;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.item.ItemService;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateStatusItemUseCase {
    private final ItemService itemService;
    private final GarageService garageService;

    public Optional<?> handler(
        User user,
        UUID itemId,
        UpdateItemStatusRequest request
    ) {
        var garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada"
            )
        );

        var item = itemService.getById(itemId, garage).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Item não encontrado!"
            )
        );

        item.setStatus(request.getStatus());

        return Optional.of(itemService.save(item));
    }
}
