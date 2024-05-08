package world.pasds.back.role.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.authority.entity.Authority;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleRequestDto {
    private Long teamId;
    private String roleName;
    private List<Authority> authorities;
}
