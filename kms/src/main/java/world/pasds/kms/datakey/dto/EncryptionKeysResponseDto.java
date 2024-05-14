package world.pasds.kms.datakey.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EncryptionKeysResponseDto {
    private String dataKey;
    private String encryptedDataKey;
    private String iv;
    private String encryptedIv;
    private Long masterKeyVersion;

}
