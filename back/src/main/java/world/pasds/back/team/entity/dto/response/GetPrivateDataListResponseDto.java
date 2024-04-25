package world.pasds.back.team.entity.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetPrivateDataListResponseDto {
    private Long organizationId;
    private Long teamId;
    private String teamName;
}
