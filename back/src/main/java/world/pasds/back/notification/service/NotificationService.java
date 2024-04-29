package world.pasds.back.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.member.entity.Member;
import world.pasds.back.notification.entity.Notification;
import world.pasds.back.notification.entity.NotificationStatus;
import world.pasds.back.notification.entity.NotificationType;
import world.pasds.back.notification.entity.dto.response.NotificationResponseDto;
import world.pasds.back.notification.repository.NotificationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationEventPublisher notificationEventPublisher;
    private final NotificationRepository notificationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notify(Member fromMember, Member toMember, String title, String message, NotificationType type, String url) {
        Notification notification = Notification.builder()
                .fromMember(fromMember.getId())
                .toMember(toMember.getId())
                .title(title)
                .message(message)
                .type(type)
                .actionUrl(url)
                .status(NotificationStatus.UNREAD)
                .build();

        notificationRepository.save(notification);

        NotificationResponseDto responseDto = NotificationResponseDto.builder()
                .fromMemberNickName(fromMember.getNickname())
                .toMemberNickName(toMember.getNickname())
                .title(title)
                .message(message)
                .type(type)
                .actionUrl(url)
                .status(NotificationStatus.UNREAD)
                .build();
        notificationEventPublisher.publish(responseDto);
    }

    @Transactional
    public int countUnreadNotifications(Long memberId) {
        return notificationRepository.countByToMemberAndStatus(memberId, NotificationStatus.UNREAD);
    }

    @Transactional
    public List<NotificationResponseDto> findAllNotifications(Long memberId, int offset) {
        Pageable pageable = PageRequest.of(offset, 10);
        List<Notification> notifications = notificationRepository.findAllNotificationsByToMember(memberId, pageable);

        return notifications.stream().map(notification -> NotificationResponseDto.builder()
                .id(notification.getId())
                .fromMemberNickName(notification.getFromMemberNickName())
                .toMemberNickName(notification.getToMemberNickName())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .actionUrl(notification.getActionUrl())
                .status(notification.getStatus())
                .type(notification.getType())
                .build()).collect(Collectors.toList());
    }

    @Transactional
    public NotificationResponseDto readNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new BusinessException(ExceptionCode.NOTIFICATION_NOT_FOUND));
        notification.setStatus(NotificationStatus.READ);

        return NotificationResponseDto.builder()
                .id(notification.getId())
                .fromMemberNickName(notification.getFromMemberNickName())
                .toMemberNickName(notification.getToMemberNickName())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .actionUrl(notification.getActionUrl())
                .status(notification.getStatus())
                .type(notification.getType())
                .build();
    }
}
