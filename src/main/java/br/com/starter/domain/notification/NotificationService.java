package br.com.starter.domain.notification;

import br.com.starter.domain.user.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void notifyUser(User user, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findByUserAndStatus(user, NotificationStatus.UNREAD);
    }

    public void markAsRead(Notification notification) {
        notification.setStatus(NotificationStatus.READ);
        notificationRepository.save(notification);
    }
}