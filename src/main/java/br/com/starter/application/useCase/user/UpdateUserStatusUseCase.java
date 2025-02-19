package br.com.starter.application.useCase.user;

import br.com.starter.application.api.user.dto.UpdateUserStatusDTO;
import br.com.starter.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateUserStatusUseCase {
    private final UserService userService;

    public Optional<?> handler(UUID userId, UpdateUserStatusDTO request){
        var user = userService.getUserById(userId);
        user.setStatus(request.getStatus());

        user = userService.save(user);

        return Optional.of(user);
    }
}
