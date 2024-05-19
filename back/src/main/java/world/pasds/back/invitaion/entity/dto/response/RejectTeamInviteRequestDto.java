package world.pasds.back.invitaion.entity.dto.response;

import lombok.Getter;

@Getter
public class RejectTeamInviteRequestDto {
    private Long organizationId;
    private Long teamId;
}
