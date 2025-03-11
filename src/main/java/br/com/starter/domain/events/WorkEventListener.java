package br.com.starter.domain.events;

import br.com.starter.domain.user.User;
import br.com.starter.domain.notification.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WorkEventListener {
    private final NotificationService notificationService;

    public WorkEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleWorkPendingTooLongEvent(WorkPendingTooLongEvent event) {
        if (event.getWork().getGarage() == null || event.getWork().getGarage().getOwner() == null) {
            System.out.println("⚠️ Work sem gerente, ignorando notificação.");
            return;
        }

        User manager = event.getWork().getGarage().getOwner();
        String message = "🚨 O trabalho " + event.getWork().getTitle() + " está PENDING há mais de 2 horas!";

        notificationService.notifyUser(manager, message);
    }
}

