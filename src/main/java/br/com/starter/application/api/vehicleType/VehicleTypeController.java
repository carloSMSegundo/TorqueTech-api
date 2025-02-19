package br.com.starter.application.api.vehicleType;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.vehicleType.dto.CreateVehicleTypeDTO;
import br.com.starter.application.useCase.vehicleType.CreateVehicleTypeUseCase;
import br.com.starter.domain.user.CustomUserDetails;
import br.com.starter.domain.vehicleType.VehicleType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/torque/api/vehicle-types")
@RequiredArgsConstructor
public class VehicleTypeController {

    private final CreateVehicleTypeUseCase createVehicleTypeUseCase;

    @PostMapping
    public ResponseEntity<?> createVehicleType(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @Valid @RequestBody CreateVehicleTypeDTO createVehicleTypeDTO
    ) {
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        createVehicleTypeUseCase.handler(createVehicleTypeDTO)
                )
        );
    }

}
