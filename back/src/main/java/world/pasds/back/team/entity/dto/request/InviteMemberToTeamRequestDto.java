package world.pasds.back.team.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteMemberToTeamRequestDto {
    private Long organizationId;
    private Long teamId;
    private String inviteMemberEmail;
    private Long roleId;
}
