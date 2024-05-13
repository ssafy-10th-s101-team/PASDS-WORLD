package world.pasds.back.organization.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.organization.entity.OrganizationRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequestDto {
    private Long organizationId;
    private Long organizationMemberId;
    private OrganizationRole newOrganizationRole;
}
