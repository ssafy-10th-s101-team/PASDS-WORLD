package world.pasds.back.team.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetTeamsResponseDto {
    private Long organizationId;
    private Long teamId;
    private String teamName;
}
