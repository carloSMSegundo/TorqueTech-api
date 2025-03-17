package br.com.starter.domain.notification;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class NotificationQueueService {
    private final Queue<Notification> notificationQueue = new LinkedList<>();

    public synchronized void addNotification(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now()); // 🔹 Define o tempo de entrada
        notificationQueue.offer(notification);
        System.out.println("🔔 Notificação adicionada à fila! Tamanho da fila: " + notificationQueue.size());
    }

    public synchronized Notification processNextNotification() {
        return notificationQueue.poll();
    }

    public synchronized Notification peekNextNotification() {
        return notificationQueue.peek(); // Apenas vê o próximo, sem removê-lo
    }


    public synchronized int getQueueSize() {
        return notificationQueue.size();
    }

    public synchronized List<Notification> getPendingNotifications() {
        return new LinkedList<>(notificationQueue); // 🔹 Retorna uma cópia segura da fila
    }

    public synchronized void clearQueue() {
        notificationQueue.clear();
        System.out.println("🚮 Fila de notificações limpa!");
    }
}
