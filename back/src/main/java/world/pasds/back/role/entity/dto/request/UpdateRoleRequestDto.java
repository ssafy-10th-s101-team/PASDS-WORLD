package world.pasds.back.role.entity.dto.request;

import lombok.Getter;
import world.pasds.back.authority.entity.Authority;

import java.util.List;

@Getter
public class UpdateRoleRequestDto {
    private Long teamId;
    private Long roleId;
    private String newRoleName;
    private List<Authority> authorities;
}
