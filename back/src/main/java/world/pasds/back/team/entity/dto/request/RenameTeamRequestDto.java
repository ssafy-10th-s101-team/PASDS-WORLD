package world.pasds.back.team.entity.dto.request;

import lombok.Getter;

@Getter
public class RenameTeamRequestDto {
    private Long teamId;
    private String newName;
}
