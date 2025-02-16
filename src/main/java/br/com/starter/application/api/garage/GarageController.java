package br.com.starter.application.api.garage;

import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.garage.dtos.CreateGarageDTO;
import br.com.starter.application.useCase.garage.CreateGarageUseCase;
import br.com.starter.application.useCase.garage.GetPageGarageUseCase;
import br.com.starter.application.useCase.garage.getGarageUseCase;
import br.com.starter.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/torque/api/garage")
@RequiredArgsConstructor
public class GarageController {

    private final CreateGarageUseCase createGarageUseCase;
    private final GetPageGarageUseCase getPageGarageUseCase;
    private final getGarageUseCase getGarageUseCase;

    @PostMapping
    public ResponseEntity<?> create(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @RequestBody CreateGarageDTO request
    ) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                createGarageUseCase.handler(request)
            )
        );
    }

    @PostMapping("/page/{page}")
    public ResponseEntity<?> page(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @PathVariable Integer page,
        @RequestBody GetPageRequest request
    ){
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getPageGarageUseCase.handler(page, request)
            )
        );
    }

    @GetMapping("/{garageId}")
    public ResponseEntity<?> get(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @PathVariable UUID garageId
    ) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getGarageUseCase.handler(garageId)
            )
        );
    }
}
