package world.pasds.back.invitaion.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInvitationsResponseDto {
    private Long invitationId;
    private String invitedBy;
    private LocalDateTime expiredAt;
    private Long organizationId;
    private String organizationName;
    private String organizationRole;
    private Long teamId;
    private String teamName;
    private String roleName;
    private Long roleId;

    @Override
    public String toString() {
        return "GetInvitationsResponseDto{" +
                "invitationId=" + invitationId +
                ", invitedBy='" + invitedBy + '\'' +
                ", expiredAt=" + expiredAt +
                ", organizationId=" + organizationId +
                ", organizationName='" + organizationName + '\'' +
                ", organizationRole='" + organizationRole + '\'' +
                ", teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
