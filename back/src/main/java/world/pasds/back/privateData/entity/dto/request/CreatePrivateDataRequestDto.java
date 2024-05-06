package world.pasds.back.privateData.entity.dto.request;

import lombok.Getter;
import world.pasds.back.privateData.entity.DataType;

import java.util.List;

@Getter
public class CreatePrivateDataRequestDto {
    private Long teamId;
    private DataType type;
    private String title;
    private String content;
    private String memo;
    private String privateDataId;
    private String url;
    private List<Long> roleId;
}
