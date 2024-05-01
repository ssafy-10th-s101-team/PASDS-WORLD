package world.pasds.kms.datakey.dto;

import lombok.Builder;

@Builder
public class RegenerateKeysResponseDto {
    private String oldDataKey;
    private String oldIv;
    private String newDataKey;
    private String newIv;
    private String encryptedNewDataKey;
    private String encryptedNewIv;
}
