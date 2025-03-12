package br.com.starter.application.useCase.notification;

import br.com.starter.domain.notification.Notification;
import br.com.starter.domain.notification.NotificationService;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetUnreadNotificationsUseCase {
    private final NotificationService notificationService;

    public List<Notification> handler(User user) {
        return notificationService.getUnreadNotifications(user);
    }
}
