package world.pasds.back.organization.entity.dto.request;

import lombok.Getter;
import world.pasds.back.organization.entity.OrganizationRole;

@Getter
public class UpdateOrganizationMemberRoleRequestDto {
    Long memberId;
    Long organizationId;
    OrganizationRole organizationRole;
}
