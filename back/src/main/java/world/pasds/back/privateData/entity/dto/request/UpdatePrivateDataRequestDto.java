package world.pasds.back.privateData.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePrivateDataRequestDto {
    private Long teamId;
    private Long privateDataId;
    private String title;
    private String content;
    private String memo;
    private String id;
    private String url;
    private List<Long> roleId;
}
