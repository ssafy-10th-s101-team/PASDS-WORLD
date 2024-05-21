package world.pasds.back.team.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamsResponseDto {
    private Long organizationId;
    private Long teamId;
    private String teamName;
}
