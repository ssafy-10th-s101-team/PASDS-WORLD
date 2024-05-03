package world.pasds.back.organization.entity.dto.request;

import lombok.Getter;
import world.pasds.back.organization.entity.OrganizationRole;

@Getter
public class InviteMemberToOrganizationRequestDto {
    private Long organizationId;
    private String email;
    private OrganizationRole organizationRole;
}
