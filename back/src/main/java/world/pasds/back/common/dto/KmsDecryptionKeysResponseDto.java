package world.pasds.back.common.dto;

import lombok.Getter;

@Getter
public class KmsDecryptionKeysResponseDto {
	private String dataKey;
	private String ivKey;
}
