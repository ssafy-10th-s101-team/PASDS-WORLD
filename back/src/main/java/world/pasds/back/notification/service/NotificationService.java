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
import world.pasds.back.member.repository.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final NotificationEventPublisher notificationEventPublisher;
    private final NotificationRepository notificationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notify(Member fromMember, Member toMember, String title, String message, NotificationType type, String url) {
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
    public List<NotificationResponseDto> findAllUnreadNotifications(Long memberId, int offset) {
        Pageable pageable = PageRequest.of(offset, 100);
//        List<Notification> notifications = notificationRepository.findAllNotificationsByToMember(memberId, pageable);
        List<Notification> notifications =  notificationRepository.findAllNotificationsByToMemberAndStatus(memberId, NotificationStatus.UNREAD, pageable);
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
    public NotificationResponseDto readNotification(Long memberId, Long notificationId) {

        //권한 확인
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new BusinessException(ExceptionCode.NOTIFICATION_NOT_FOUND));
        if(notification.getToMember() != member.getId())
            throw new BusinessException(ExceptionCode.MEMBER_UNAUTHORIZED);

        notification.setStatus(NotificationStatus.READ);

        notificationRepository.save(notification);

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
