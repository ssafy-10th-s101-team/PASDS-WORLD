package world.pasds.back.dashboard.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamDashboardResponseDto {

    Long teamId;
    String teamName;
    int data;

}
