package world.pasds.back.common.dto;

import lombok.Getter;

@Getter
public class KmsDecryptionKeysResponseDto {
	private byte[] dataKey;
	private byte[] ivKey;
}
