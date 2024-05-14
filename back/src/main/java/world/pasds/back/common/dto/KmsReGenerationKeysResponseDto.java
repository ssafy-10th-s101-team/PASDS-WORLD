package world.pasds.back.common.dto;


import lombok.Getter;

@Getter
public class KmsReGenerationKeysResponseDto {
    private String oldDataKey;
    private String oldIv;
    private String newDataKey;
    private String newIv;
    private String encryptedNewDataKey;
    private String encryptedNewIv;
    private Long masterKeyVersion;
}
