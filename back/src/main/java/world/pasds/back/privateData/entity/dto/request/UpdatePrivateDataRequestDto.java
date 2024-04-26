package world.pasds.back.privateData.entity.dto.request;

import lombok.Getter;

@Getter
public class UpdatePrivateDataRequestDto {
    private Long teamId;
    private Long id;
    private String title;
    private String content;
    private String memo;
    private String privateDataId;
    private String url;
    private Long roleId;
}
