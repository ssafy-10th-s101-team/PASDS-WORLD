package world.pasds.back.common.dto;

import lombok.Getter;

@Getter
public class KmsEncryptionKeysResponseDto {
	private String dataKey;
	private String encryptedDataKey;
	private String iv;
	private String encryptedIv;
	private Long masterKeyVersion;
}



