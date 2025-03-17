package br.com.starter.domain.events;

import br.com.starter.domain.notification.Notification;
import br.com.starter.domain.notification.NotificationQueueService;
import br.com.starter.domain.notification.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationScheduler {

    private final NotificationQueueService notificationQueueService;
    private final NotificationService notificationService;

    public NotificationScheduler(NotificationQueueService notificationQueueService, NotificationService notificationService) {
        this.notificationQueueService = notificationQueueService;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 5000) // Verifica a cada 5 segundos
    public void processNotifications() {
        Notification notification = notificationQueueService.peekNextNotification();
        if (notification != null) {
            // Processa a notificação
            notificationService.notifyUser(notification.getUser(), notification.getMessage());
        }
    }
}

