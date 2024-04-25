package world.pasds.back.team.entity.dto.request;

import lombok.Getter;

@Getter
public class RemoveMemberFromTeamRequestDto {
    private Long teamId;
    private String removeMemberEmail;
}
