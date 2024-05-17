package world.pasds.back.notification.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.notification.entity.NotificationStatus;
import world.pasds.back.notification.entity.NotificationType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {
    private Long id;
    private Long toMemberId;
    private String toMemberNickName;
    private String fromMemberNickName;
    private String title;
    private String message;
    private String actionUrl;
    private NotificationStatus status;
    private NotificationType type;
}
