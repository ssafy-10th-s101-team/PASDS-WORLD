package world.pasds.kms.datakey.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EncryptedDataKeyDto {
    String encryptedDataKey;
    String encryptedIv;
    Long masterKeyVersion;
}
