package world.pasds.back.organization.entity.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetOrganizationsResponseDto {
    private Long organizationId;
    private String name;
    private Integer teamCount;
}
