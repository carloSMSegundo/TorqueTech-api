package br.com.starter.application.api.work;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.work.dtos.CreateWorkRequestDTO;
import br.com.starter.application.useCase.work.CreateWorkRequestUseCase;
import br.com.starter.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/torque/api/work")
@RequiredArgsConstructor
public class WorkController {

    private final CreateWorkRequestUseCase createWorkRequestUseCase;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @RequestBody CreateWorkRequestDTO request
    ) {
        var owner = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        createWorkRequestUseCase.handler(request, owner)
                )
        );
    }
}
