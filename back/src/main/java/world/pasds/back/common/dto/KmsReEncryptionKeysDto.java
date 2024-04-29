package world.pasds.back.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KmsReEncryptionKeysDto {
    String encryptedDataKey;
    String encryptedIv;
}
