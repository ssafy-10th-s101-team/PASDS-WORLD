package world.pasds.back.privateData.entity.dto.request;

import lombok.Getter;

@Getter
public class GetPrivateDataRequestDto {
    private Long teamId;
    private Long privateDataId;
}
