package world.pasds.back.invitaion.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInvitationsResponseDto {
    private Long invitationId;
    private String invitedBy;
    private LocalDateTime expiredAt;
    private String organizationName;
    private String organizationRole;
    private String teamName;
    private String roleName;
}
