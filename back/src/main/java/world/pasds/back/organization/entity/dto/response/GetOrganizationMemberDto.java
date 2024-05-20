package world.pasds.back.organization.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.organization.entity.OrganizationRole;
import world.pasds.back.team.entity.dto.response.GetTeamsResponseDto;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationMemberDto {
    private Long memberId;
    private String name;
    private OrganizationRole organizationRole;
    private String email;
    private List<GetTeamsResponseDto> teams; //현재조직 내 가지고 있는 팀
}
