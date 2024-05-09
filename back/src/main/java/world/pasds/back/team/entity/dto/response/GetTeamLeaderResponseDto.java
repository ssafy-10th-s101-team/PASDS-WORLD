package world.pasds.back.team.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamLeaderResponseDto {
    private Long id;
    private String nickname;
}
