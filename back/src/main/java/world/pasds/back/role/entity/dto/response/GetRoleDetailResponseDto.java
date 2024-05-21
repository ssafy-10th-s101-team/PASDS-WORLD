package world.pasds.back.role.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.authority.entity.AuthorityDto;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRoleDetailResponseDto {
    private String name;
    private List<AuthorityDto> authorities;
}
