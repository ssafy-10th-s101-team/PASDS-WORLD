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
}
