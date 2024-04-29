package world.pasds.kms.datakey.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReEncryptionDto {
    String encryptedDataKey;
    String encryptedIv;
}
