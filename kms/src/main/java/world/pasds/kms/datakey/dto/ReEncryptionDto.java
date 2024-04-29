package world.pasds.kms.datakey.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReEncryptionDto {
    String encryptedDataKey;
    String encryptedIv;
}
