package world.pasds.back.invitaion.entity.dto.request;

import lombok.Getter;

@Getter
public class AcceptTeamInviteRequestDto {
    private Long organizationId;
    private Long teamId;
    private Long roleId;
}
