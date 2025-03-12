package br.com.starter.application.useCase.notification;

import br.com.starter.domain.notification.Notification;
import br.com.starter.domain.notification.NotificationRepository;
import br.com.starter.domain.notification.NotificationStatus;
import br.com.starter.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MarkNotificationAsReadUseCase {
    private final NotificationRepository notificationRepository;

    public Notification handler(UUID notificationId, User user) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada!"));

        if (!Objects.equals(notification.getUser().getId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não tem permissão para modificar esta notificação!");
        }

        notification.setStatus(NotificationStatus.READ);
        return notificationRepository.save(notification);
    }
}
