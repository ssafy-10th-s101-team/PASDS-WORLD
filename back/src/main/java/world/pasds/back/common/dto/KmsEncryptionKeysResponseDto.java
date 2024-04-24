package world.pasds.back.common.dto;

import lombok.Getter;

@Getter
public class KmsEncryptionKeysResponseDto {
	private byte[] dataKey;
	private byte[] encryptedDataKey;
	private byte[] ivKey;
	private byte[] encryptedIvKey;
}



