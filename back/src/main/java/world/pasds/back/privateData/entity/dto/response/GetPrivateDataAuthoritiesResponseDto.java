package world.pasds.back.privateData.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.authority.entity.AuthorityName;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPrivateDataAuthoritiesResponseDto {
    private AuthorityName name;
}
