package world.pasds.back.role.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.authority.entity.Authority;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRoleResponseDto {
    private Long roleId;
    private String name;
    private List<Authority> authorities;
}
