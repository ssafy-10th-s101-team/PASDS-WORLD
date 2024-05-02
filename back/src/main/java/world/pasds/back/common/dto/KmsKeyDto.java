package world.pasds.back.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class KmsKeyDto {
    String encryptedDataKey;
    String encryptedIv;
}
