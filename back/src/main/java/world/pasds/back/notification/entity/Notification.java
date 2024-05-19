package world.pasds.back.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import world.pasds.back.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long toMember;
    private String toMemberNickName;

    private Long fromMember;
    private String fromMemberNickName;

    private String title;

    private String message;

    private String actionUrl;

    @Setter
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

}
