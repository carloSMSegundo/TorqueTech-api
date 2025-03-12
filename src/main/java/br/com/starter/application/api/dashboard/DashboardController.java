package br.com.starter.application.api.dashboard;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.domain.dasboard.DashboardService;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/torque/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    private final GarageService garageService;

    @GetMapping
    public ResponseEntity<?> getDashboard(@AuthenticationPrincipal CustomUserDetails userAuthentication) {
        var user = userAuthentication.getUser();

        Garage garage = garageService.getByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "O usuário não possui uma oficina registrada"
                ));

        var metrics = dashboardService.getMetricsForGarage(garage.getId());

        return ResponseEntity.ok(new ResponseDTO<>(metrics));
    }
}
