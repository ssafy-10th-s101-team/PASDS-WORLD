package world.pasds.back.privateData.entity.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdatePrivateDataRoleRequestDto {
    private Long teamId;
    private Long privateDataId;
    private List<Long> roleId;

}
