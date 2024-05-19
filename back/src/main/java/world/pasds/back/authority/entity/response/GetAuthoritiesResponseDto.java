package world.pasds.back.authority.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.authority.entity.AuthorityName;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAuthoritiesResponseDto {
    private Long id;
    private AuthorityName name;
}
