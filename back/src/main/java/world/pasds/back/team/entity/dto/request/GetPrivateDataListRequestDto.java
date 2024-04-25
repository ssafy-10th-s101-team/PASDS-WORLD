package world.pasds.back.team.entity.dto.request;

import lombok.Getter;
import world.pasds.back.team.entity.DataType;

@Getter
public class GetPrivateDataListRequestDto {
    private Long organizationId;
    private Long teamId;
    private DataType type;
    private String title;
    private String createdBy;
}
