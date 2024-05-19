package world.pasds.back.role.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRoleRequestDto {
    private Long teamId;
    private Long roleId;
}
