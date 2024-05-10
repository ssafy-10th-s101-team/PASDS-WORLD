package world.pasds.back.privateData.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPrivateDataRolesResponseDto {
    private Map<Long, String> roles;
    private List<Long> hasAuthorities;
}
