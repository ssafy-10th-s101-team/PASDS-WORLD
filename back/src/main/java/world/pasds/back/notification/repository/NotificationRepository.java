package world.pasds.back.notification.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import world.pasds.back.notification.entity.Notification;
import world.pasds.back.notification.entity.NotificationStatus;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    int countByToMemberAndStatus(Long toMember, NotificationStatus status);

    List<Notification> findAllNotificationsByToMember(Long userId, Pageable pageable);
}
