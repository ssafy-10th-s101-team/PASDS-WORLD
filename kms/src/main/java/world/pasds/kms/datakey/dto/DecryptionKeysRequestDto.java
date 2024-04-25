package world.pasds.kms.datakey.dto;

import lombok.Getter;

@Getter
public class DecryptionKeysRequestDto {
    private String encryptedDataKey;
    private String encryptedIv;

}
