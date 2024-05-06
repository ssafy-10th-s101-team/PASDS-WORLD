package world.pasds.back.privateData.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPrivateDataResponseDto {
    private String privateData;
}
