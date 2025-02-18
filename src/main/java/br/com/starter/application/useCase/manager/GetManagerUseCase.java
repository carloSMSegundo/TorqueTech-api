package br.com.starter.application.useCase.manager;

import br.com.starter.domain.manager.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetManagerUseCase {
    private final ManagerService managerService;

    public Optional<?> handler(UUID managerId) {
        return managerService.getById(managerId);
    }
}
