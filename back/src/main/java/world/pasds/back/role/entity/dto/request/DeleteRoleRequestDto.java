package world.pasds.back.role.entity.dto.request;

import lombok.Getter;

@Getter
public class DeleteRoleRequestDto {
    private Long teamId;
    private Long roleId;
}
