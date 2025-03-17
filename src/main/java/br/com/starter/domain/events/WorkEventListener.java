package br.com.starter.domain.events;

import br.com.starter.domain.notification.Notification;
import br.com.starter.domain.notification.NotificationQueueService;
import br.com.starter.domain.user.User;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WorkEventListener {
    private final NotificationQueueService notificationQueueService;

    public WorkEventListener(NotificationQueueService notificationQueueService) {
        this.notificationQueueService = notificationQueueService;
    }

    @EventListener
    public void handleWorkPendingTooLongEvent(WorkPendingTooLongEvent event) {
        User manager = event.getWork().getGarage().getOwner();

        if (manager == null) {
            System.out.println("⚠ Erro: Gerente não encontrado para o trabalho " + event.getWork().getTitle());
            return;
        }

        Notification notification = new Notification();
        notification.setUser(manager);
        notification.setMessage("O trabalho '" + event.getWork().getTitle() + "' está pendente há mais de 2 horas!");

        notificationQueueService.addNotification(notification);

        // 🔹 Logs melhorados para depuração
        System.out.println("✅ Notificação adicionada à fila para " + manager.getProfile().getName());
        System.out.println("📢 Mensagem: " + notification.getMessage());
    }
}
