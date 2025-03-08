package br.com.starter.application.api.workOrder;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.work.dtos.UpdateWorkDTO;
import br.com.starter.application.api.workOrder.dtos.CreateWorkOrderRequestDTO;
import br.com.starter.application.api.workOrder.dtos.UpdateWorkOrderDTO;
import br.com.starter.application.useCase.workOrder.CreateWorkOrderRequestUseCase;
import br.com.starter.application.useCase.workOrder.UpdateWorkOrderUseCase;
import br.com.starter.domain.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/torque/api/work-order")
@RequiredArgsConstructor
public class WorkOrderController {
    private final CreateWorkOrderRequestUseCase createWorkOrderRequestUseCase;
    private final UpdateWorkOrderUseCase updateWorkOrderUseCase;

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

    @PutMapping("/{workId}/{workOrderId}")
    public ResponseEntity<?> update(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @Valid @RequestBody UpdateWorkOrderDTO request,
            @PathVariable UUID workOrderId,
            @PathVariable UUID workId
    ) {
        var owner = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        updateWorkOrderUseCase.handler(workOrderId, workId, owner, request)
                )
        );
    }
}