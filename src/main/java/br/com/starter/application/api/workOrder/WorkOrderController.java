package br.com.starter.application.api.workOrder;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.workOrder.dtos.CreateWorkOrderRequestDTO;
import br.com.starter.application.useCase.workOrder.CreateWorkOrderRequestUseCase;
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
@RequestMapping("/torque/api/work-order")
@RequiredArgsConstructor
public class WorkOrderController {
    private final CreateWorkOrderRequestUseCase createWorkOrderRequestUseCase;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @Valid @RequestBody CreateWorkOrderRequestDTO request
    ) {
        var owner = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        createWorkOrderRequestUseCase.handler(request, owner)
                )
        );
    }
}