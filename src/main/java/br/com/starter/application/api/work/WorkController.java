package br.com.starter.application.api.work;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.work.dtos.CreateWorkRequestDTO;
import br.com.starter.application.api.work.dtos.GetPageWorkRequest;
import br.com.starter.application.useCase.work.CreateWorkRequestUseCase;
import br.com.starter.application.useCase.work.GetPageWorkUseCase;
import br.com.starter.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/torque/api/work")
@RequiredArgsConstructor
public class WorkController {

    private final CreateWorkRequestUseCase createWorkRequestUseCase;
    private final GetPageWorkUseCase getPageWorkUseCase;

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



}
