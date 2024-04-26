package world.pasds.back.role.entity.dto.request;

import lombok.Getter;
import world.pasds.back.authority.entity.Authority;

import java.util.List;

@Getter
public class CreateRoleRequestDto {
    private Long teamId;
    private String roleName;
    private List<Authority> authorities;
}
