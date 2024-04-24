package world.pasds.back.organization.entity.dto.request;

import lombok.Getter;

@Getter
public class InviteMemberToOrganizationRequestDto {
    private Long organizationId;
    private String email;
}
