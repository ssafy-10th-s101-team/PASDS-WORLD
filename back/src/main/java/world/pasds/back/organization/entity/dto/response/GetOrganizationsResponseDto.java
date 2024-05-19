package world.pasds.back.organization.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import world.pasds.back.organization.entity.OrganizationRole;

@Getter
@AllArgsConstructor
public class GetOrganizationsResponseDto {
    private Long organizationId;
    private String name;
    private Integer teamCount;
    private OrganizationRole role; //로그인한 유저의 역할.
}
