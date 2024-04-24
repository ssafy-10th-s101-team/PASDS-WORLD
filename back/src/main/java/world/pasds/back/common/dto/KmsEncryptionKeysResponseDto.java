package world.pasds.back.common.dto;

import lombok.Getter;

@Getter
public class KmsEncryptionKeysResponseDto {
	private String dataKey;
	private String encryptedDataKey;
	private String ivKey;
	private String encryptedIvKey;
}



