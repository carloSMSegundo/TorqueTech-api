package br.com.starter.application.api.vehicleType;

import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.vehicleType.dtos.CreateVehicleTypeDTO;
import br.com.starter.application.api.vehicleType.dtos.UpdateVehicleTypeDTO;
import br.com.starter.application.useCase.vehicleType.*;
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
    private final GetAllVehicleTypeUseCase getAllVehicleTypeUseCase;
    private final GetPageVehicleTypeUseCase getPageVehicleTypeUseCase;

    @PostMapping
    public ResponseEntity<?> createVehicleType(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody CreateVehicleTypeDTO createVehicleTypeDTO
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                createVehicleTypeUseCase.handler(user, createVehicleTypeDTO)
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
    public ResponseEntity<?> getById(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @PathVariable UUID vehicleTypeId
    ) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getVehicleTypeUseCase.handler(vehicleTypeId)
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
                getPageVehicleTypeUseCase.handler(page, request)
            )
        );
    }

    @GetMapping
    public ResponseEntity<?> get(
        @AuthenticationPrincipal CustomUserDetails userAuthentication
    ) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getAllVehicleTypeUseCase.handler()
            )
        );
    }
}
