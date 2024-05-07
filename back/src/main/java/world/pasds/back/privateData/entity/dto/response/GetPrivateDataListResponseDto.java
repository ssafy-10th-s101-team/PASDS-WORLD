package world.pasds.back.privateData.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import world.pasds.back.privateData.entity.DataType;

@Getter
@AllArgsConstructor
public class GetPrivateDataListResponseDto {
    private Long teamId;
    private Long privateDataId;
    private String title;
    private DataType type;
    private String createdBy;
    private String dataId;
    private String url;
}
