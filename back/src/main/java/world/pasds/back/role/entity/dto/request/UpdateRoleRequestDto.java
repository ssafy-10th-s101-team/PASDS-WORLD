package world.pasds.back.role.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequestDto {
    private Long teamId;
    private Long roleId;
    private String newRoleName;
    private List<Long> authorities;
}
