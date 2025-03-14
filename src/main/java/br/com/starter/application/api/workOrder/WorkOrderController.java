package br.com.starter.application.api.workOrder;

import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.workOrder.dtos.CreateWorkOrderRequestDTO;
import br.com.starter.application.api.workOrder.dtos.UpdateWorkOrderDTO;
import br.com.starter.application.api.workOrder.dtos.UpdateWorkOrderStatusRequest;
import br.com.starter.application.useCase.workOrder.*;
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
    private final UpdateWorkOrderStatusUseCase updateWorkOrderStatusUseCase;
    private final GetListWorkOrderUseCase getListWorkOrderUseCase;
    private final GetPageWorkOrderUseCase getPageWorkOrderUseCase;

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

    @PutMapping("/{workOrderId}")
    public ResponseEntity<?> update(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @Valid @RequestBody UpdateWorkOrderDTO request,
            @PathVariable UUID workOrderId
    ) {
        var owner = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                updateWorkOrderUseCase.handler(workOrderId, owner, request)
            )
        );
    }

    @PutMapping("/{workOrderId}/status")
    public ResponseEntity<?> updateStatus(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody UpdateWorkOrderStatusRequest request,
        @PathVariable UUID workOrderId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                updateWorkOrderStatusUseCase.handler(user, workOrderId, request)
            )
        );
    }

    @GetMapping("/{workId}")
    public ResponseEntity<?> getList(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @PathVariable UUID workId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        getListWorkOrderUseCase.handler(user, workId)
                )
        );
    }

    @GetMapping("/{workId}/page/{page}")
    public ResponseEntity<?> getPage(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @PathVariable UUID workId,
            @PathVariable Integer page,
            @RequestBody GetPageRequest request
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        getPageWorkOrderUseCase.handler(user, workId, page, request)
                )
        );
    }
}