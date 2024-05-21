package world.pasds.back.privateData.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.privateData.entity.DataType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateDataResponse {
    private Long teamId;
    private Long privateDataId;
    private String title;
    private DataType type;
    private String createdBy;
    private String dataId;
    private String url;
}
