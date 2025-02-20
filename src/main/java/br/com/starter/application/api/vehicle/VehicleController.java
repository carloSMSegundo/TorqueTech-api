package br.com.starter.application.api.vehicle;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.vehicle.dtos.CreateVehicleDTO;
import br.com.starter.application.useCase.vehicle.CreateVehicleUseCase;
import br.com.starter.domain.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/torque/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final CreateVehicleUseCase createVehicleUseCase;

    @PostMapping
    public ResponseEntity<?> create(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @Valid @RequestBody CreateVehicleDTO request
    ) {
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        createVehicleUseCase.handler(request)
                )
        );
    }
}
