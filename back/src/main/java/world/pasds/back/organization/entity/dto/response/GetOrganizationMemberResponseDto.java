package world.pasds.back.organization.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationMemberResponseDto {
    private String name;
    private boolean isHeader;
}
