package world.pasds.back.dashboard.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MainDashboardResponseDto {

    private List<int[]> organizationViewList;
    private List<int[]> organizationRotateList;
    private List<int[]> organizationCountList;


}
