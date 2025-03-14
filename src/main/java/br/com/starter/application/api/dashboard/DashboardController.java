package br.com.starter.application.api.dashboard;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.dashboard.dto.MetricRequest;
import br.com.starter.domain.dasboard.DashboardService;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/torque/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @PostMapping("/metrics")
    public ResponseEntity<?> getDashboard(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @RequestBody MetricRequest request
    ) {
        var user = userAuthentication.getUser();
        var metrics = dashboardService.getMetricsForGarage(user, request);

        return ResponseEntity.ok(
            new ResponseDTO<>(metrics)
        );
    }
}
