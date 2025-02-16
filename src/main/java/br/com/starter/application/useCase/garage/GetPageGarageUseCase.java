package br.com.starter.application.useCase.garage;

import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.domain.garage.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPageGarageUseCase {
    private final GarageService garageService;

    public Optional<?> handler(Integer page, GetPageRequest request) {
        return Optional.of(
            garageService.findAllPage(
                PageRequest.of(page, request.getSize())
            )
        );
    }
}
