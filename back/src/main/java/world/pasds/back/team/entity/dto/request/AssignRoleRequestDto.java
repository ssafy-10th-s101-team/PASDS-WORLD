package world.pasds.back.team.entity.dto.request;

import lombok.Getter;

@Getter
public class AssignRoleRequestDto {
    private Long assignedMemberId;
    private Long roleId;
    private Long teamId;
}
