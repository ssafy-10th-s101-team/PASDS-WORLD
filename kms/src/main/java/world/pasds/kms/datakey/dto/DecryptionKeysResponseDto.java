package world.pasds.kms.datakey.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DecryptionKeysResponseDto {
    private String dataKey;
    private String iv;

}
