package world.pasds.back.organization.entity.dto.request;

import lombok.Getter;

@Getter
public class RenameOrganizationRequestDto {
    private Long organizationId;
    private String newName;
}
