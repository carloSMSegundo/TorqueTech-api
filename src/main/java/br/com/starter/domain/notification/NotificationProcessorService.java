package br.com.starter.domain.notification;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationProcessorService {
    private final NotificationQueueService notificationQueueService;
    private final NotificationService notificationService;

    public NotificationProcessorService(NotificationQueueService notificationQueueService, NotificationService notificationService) {
        this.notificationQueueService = notificationQueueService;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 5000) // Verifica a cada 5 segundos
    public void processNotifications() {
        // Não remove a notificação da fila
        Notification notification = notificationQueueService.peekNextNotification();

        if (notification != null) {
            // Processa a notificação
            notificationService.notifyUser(notification.getUser(), notification.getMessage());
        }
    }
}
