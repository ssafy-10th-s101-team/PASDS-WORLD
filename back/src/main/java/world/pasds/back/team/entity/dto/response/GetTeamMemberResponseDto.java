package world.pasds.back.team.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamMemberResponseDto {
    private Long id;
    private String memberNickname;
    private String role;
}
