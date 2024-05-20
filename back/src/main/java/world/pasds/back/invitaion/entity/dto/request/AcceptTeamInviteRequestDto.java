package world.pasds.back.invitaion.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AcceptTeamInviteRequestDto {
    private Long organizationId;
    private Long teamId;
    private Long roleId;
}
