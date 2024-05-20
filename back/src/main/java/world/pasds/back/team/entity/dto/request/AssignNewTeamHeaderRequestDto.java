package world.pasds.back.team.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssignNewTeamHeaderRequestDto {
    private Long teamId;
    private Long newLeaderId;
}
