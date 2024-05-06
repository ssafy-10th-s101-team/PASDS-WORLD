package world.pasds.back.organization.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.organization.entity.OrganizationRole;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationMemberResponseDto {
    private String name;
    private OrganizationRole organizationRole;
}
