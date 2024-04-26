package world.pasds.back.team.entity.dto.response;

import lombok.AllArgsConstructor;
import world.pasds.back.privateData.entity.DataType;

@AllArgsConstructor
public class GetPrivateDataListResponseDto {
    private Long teamId;
    private Long privateDataId;
    private String title;
    private DataType type;
    private String createdBy;
}
