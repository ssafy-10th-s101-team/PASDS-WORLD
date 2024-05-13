package world.pasds.back.organization.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrganizationMemberResponseDto {
    private int totalPages;
    private List<GetOrganizationMemberDto> privateDataResponse;
}
