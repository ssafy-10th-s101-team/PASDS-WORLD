package world.pasds.back.privateData.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.privateData.entity.DataType;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
