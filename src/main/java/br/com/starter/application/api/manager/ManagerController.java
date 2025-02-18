package br.com.starter.application.api.manager;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.manager.dtos.CreateManagerDTO;
import br.com.starter.application.useCase.manager.CreateManagerUseCase;
import br.com.starter.domain.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/torque/api/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final CreateManagerUseCase createManagerUseCase;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @Valid @RequestBody CreateManagerDTO request
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        createManagerUseCase.handler(request, user)
                )
        );
    }
}