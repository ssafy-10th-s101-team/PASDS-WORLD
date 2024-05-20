package world.pasds.back.team.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAdminTeamsResponseDto {
    private Long organizationId;
    private Long teamId;
    private String teamName;
    private String role; //팀에서 로그인한 사용자의 역할
    private Integer secretCount; // 비밀 수
}