package br.com.starter.application.api.work;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.local.dtos.UpdateLocalStatusRequest;
import br.com.starter.application.api.work.dtos.*;
import br.com.starter.application.useCase.work.*;
import br.com.starter.domain.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/torque/api/work")
@RequiredArgsConstructor
public class WorkController {

    private final CreateWorkRequestUseCase createWorkRequestUseCase;
    private final GetPageWorkUseCase getPageWorkUseCase;
    private final UpdateWorkUseCase updateWorkUseCase;
    private final GetPageCustomerWorkUseCase getPageCustomerWorkUseCase;
    private final GetPageMechanicWorkUseCase getPageMechanicWorkUseCase;
    private final UpdateWorkStatusUseCase updateWorkStatusUseCase;

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

    @PutMapping("/search/{page}")
    public ResponseEntity<?> search(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @RequestBody GetPageWorkRequest request,
            @PathVariable Integer page
    ) {
        var owner = userAuthentication.getUser();
        Page<?> worksPage = getPageWorkUseCase.handler(owner, request, page);

        return ResponseEntity.ok(new ResponseDTO<>(worksPage));
    }

    @PutMapping("/search/{customerId}/{page}")
    public ResponseEntity<?> searchCustomer(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @RequestBody GetPageCustomerRequest request,
            @PathVariable UUID customerId,
            @PathVariable Integer page
    ) {
        var owner = userAuthentication.getUser();
        Page<?> worksPage = getPageCustomerWorkUseCase.handler(owner,customerId, page, request);

        return ResponseEntity.ok(new ResponseDTO<>(worksPage));
    }

    @PutMapping("/search/{mechanicId}/{page}")
    public ResponseEntity<?> searchMechanic(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @RequestBody GetPageMechanicRequest request,
            @PathVariable UUID mechanicId,
            @PathVariable Integer page
    ) {
        var owner = userAuthentication.getUser();
        Page<?> worksPage = getPageMechanicWorkUseCase.handler(owner,mechanicId, page, request);

        return ResponseEntity.ok(new ResponseDTO<>(worksPage));
    }

    @PutMapping("/{workId}")
    public ResponseEntity<?> update(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @Valid @RequestBody UpdateWorkDTO request,
            @PathVariable UUID workId
    ) {
        var owner = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        updateWorkUseCase.handler(workId, owner, request)
                )
        );
    }

    @PutMapping("/{workId}/status")
    public ResponseEntity<?> updateStatus(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @Valid @RequestBody UpdateWorkStatusRequest request,
            @PathVariable UUID workId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        updateWorkStatusUseCase.handler(user, workId, request)
                )
        );
    }

}
