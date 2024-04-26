package world.pasds.back.privateData.entity.dto.request;

import lombok.Getter;
import world.pasds.back.team.entity.DataType;

@Getter
public class CreatePrivateDataRequestDto {
    private Long teamId;
    private DataType type;
    private String title;
    private String content;
    private String memo;
    private Long roleId;
}
