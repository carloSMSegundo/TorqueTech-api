package br.com.starter.application.useCase.mechanic;

import br.com.starter.domain.mechanic.MechanicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetMechanicUseCase {
    private final MechanicService mechanicService;

    public Optional<?> handler(UUID mechanicId) {
        return mechanicService.getById(mechanicId);
    }
}
