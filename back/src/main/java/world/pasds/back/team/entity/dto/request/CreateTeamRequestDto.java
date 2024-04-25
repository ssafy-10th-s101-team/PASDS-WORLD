package world.pasds.back.team.entity.dto.request;

import lombok.Getter;

@Getter
public class CreateTeamRequestDto {
    private Long organizationId;
    private String teamName;
}
