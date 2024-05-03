package world.pasds.back.team.entity.dto.request;

import lombok.Getter;

@Getter
public class InviteMemberToTeamRequestDto {
    private Long organizationId;
    private Long teamId;
    private String inviteMemberEmail;
    private Long roleId;
}
