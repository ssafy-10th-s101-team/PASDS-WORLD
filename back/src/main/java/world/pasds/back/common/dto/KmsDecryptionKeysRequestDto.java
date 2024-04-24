package world.pasds.back.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KmsDecryptionKeysRequestDto {
	private String encryptedDataKey;
	private String encryptedIvKey;
}
