package br.com.starter.domain.notification;

import br.com.starter.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    @Query("""
    SELECT n FROM Notification n
    WHERE n.user = :user
    AND n.status = :status
    """)
    List<Notification> findByUserAndStatus(
            @Param("user") User user,
            @Param("status") NotificationStatus status
    );

}
