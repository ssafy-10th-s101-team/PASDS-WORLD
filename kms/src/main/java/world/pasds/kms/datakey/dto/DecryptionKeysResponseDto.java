package world.pasds.kms.datakey.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DecryptionKeysResponseDto {
    private String dataKey;
    private String iv;

}
