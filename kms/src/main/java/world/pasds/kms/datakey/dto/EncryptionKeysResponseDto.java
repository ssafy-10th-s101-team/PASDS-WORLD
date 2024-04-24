package world.pasds.kms.datakey.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncryptionKeysResponseDto {
    private String dataKey;
    private String encryptedDataKey;
    private String iv;
    private String encryptedIv;

}
