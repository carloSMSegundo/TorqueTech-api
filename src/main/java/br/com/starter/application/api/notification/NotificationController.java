package br.com.starter.application.api.notification;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.useCase.notification.GetUnreadNotificationsUseCase;
import br.com.starter.application.useCase.notification.MarkNotificationAsReadUseCase;
import br.com.starter.domain.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/torque/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final GetUnreadNotificationsUseCase getUnreadNotificationsUseCase;
    private final MarkNotificationAsReadUseCase markNotificationAsReadUseCase;

    @GetMapping
    public ResponseEntity<?> getUnreadNotifications(
            @AuthenticationPrincipal CustomUserDetails userAuthentication
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        getUnreadNotificationsUseCase.handler(user)
                )
        );
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<?> markAsRead(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @PathVariable UUID notificationId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        markNotificationAsReadUseCase.handler(notificationId, user)
                )
        );
    }
}
