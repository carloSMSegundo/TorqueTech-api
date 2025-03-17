package br.com.starter.application.api.notification;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.useCase.notification.GetUnreadNotificationsUseCase;
import br.com.starter.application.useCase.notification.MarkNotificationAsReadUseCase;
import br.com.starter.domain.notification.Notification;
import br.com.starter.domain.notification.NotificationQueueService;
import br.com.starter.domain.notification.NotificationService;
import br.com.starter.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/torque/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final GetUnreadNotificationsUseCase getUnreadNotificationsUseCase;
    private final MarkNotificationAsReadUseCase markNotificationAsReadUseCase;
    private final NotificationQueueService notificationQueueService;
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getUnreadNotifications(
            @AuthenticationPrincipal CustomUserDetails userAuthentication
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(getUnreadNotificationsUseCase.handler(user))
        );
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<?> markAsRead(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @PathVariable UUID notificationId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(markNotificationAsReadUseCase.handler(notificationId, user))
        );
    }

    // 🔹 Novo endpoint: Retorna o tamanho da fila de notificações
    @GetMapping("/queue/size")
    public ResponseEntity<?> getQueueSize() {
        int queueSize = notificationQueueService.getQueueSize();
        return ResponseEntity.ok(new ResponseDTO<>(queueSize));
    }

    // 🔹 Novo endpoint: Retorna todas as notificações pendentes na fila
    @GetMapping("/queue/pending")
    public ResponseEntity<?> getPendingNotifications() {
        List<Notification> pendingNotifications = notificationQueueService.getPendingNotifications();
        return ResponseEntity.ok(new ResponseDTO<>(pendingNotifications));
    }

    // 🔹 Novo endpoint: Limpa a fila de notificações
    @DeleteMapping("/queue/clear")
    public ResponseEntity<?> clearQueue() {
        notificationQueueService.clearQueue();
        return ResponseEntity.ok(new ResponseDTO<>("Fila de notificações limpa com sucesso!"));
    }

    @GetMapping("/queue/process-next")
    public ResponseEntity<?> processNextNotification() {
        Notification notification = notificationQueueService.processNextNotification(); // Remove e processa a notificação
        if (notification == null) {
            return ResponseEntity.ok("Não há notificações na fila para processar.");
        }

        // Envia a notificação
        notificationService.notifyUser(notification.getUser(), notification.getMessage());
        return ResponseEntity.ok("Notificação processada e removida da fila: " + notification.getMessage());
    }

}
