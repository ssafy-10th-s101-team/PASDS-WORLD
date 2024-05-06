package world.pasds.back.role.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRoleResponseDto {
    private Long roleId;
    private String name;
}
