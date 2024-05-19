package world.pasds.back.team.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamMemberResponseDto {
    private int totalPages;
    private List<GetTeamMemberDto> teamMemberResponse;
}
