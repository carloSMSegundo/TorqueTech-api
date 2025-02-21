package br.com.starter.application.api.vehicleType;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.vehicleType.dtos.CreateVehicleTypeDTO;
import br.com.starter.application.api.vehicleType.dtos.UpdateVehicleTypeDTO;
import br.com.starter.application.useCase.vehicleType.CreateVehicleTypeUseCase;
import br.com.starter.application.useCase.vehicleType.GetVehicleTypeUseCase;
import br.com.starter.application.useCase.vehicleType.UpdateVehicleTypeUseCase;
import br.com.starter.domain.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/torque/api/vehicle-types")
@RequiredArgsConstructor
public class VehicleTypeController {

    private final CreateVehicleTypeUseCase createVehicleTypeUseCase;
    private final GetVehicleTypeUseCase getVehicleTypeUseCase;
    private final UpdateVehicleTypeUseCase updateVehicleTypeUseCase;

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

    @PutMapping("/{vehicleTypeId}")
    public ResponseEntity<?> update(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @Valid @RequestBody UpdateVehicleTypeDTO request,
            @PathVariable UUID vehicleTypeId
    ) {
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        updateVehicleTypeUseCase.handler(vehicleTypeId, request)
                )
        );
    }

    @GetMapping("/{vehicleTypeId}")
    public ResponseEntity<?> get(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @PathVariable UUID vehicleTypeId
    ) {
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        getVehicleTypeUseCase.handler(vehicleTypeId)
                )
        );

    }

}
