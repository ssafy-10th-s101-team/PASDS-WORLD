package world.pasds.back.privateData.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import world.pasds.back.privateData.entity.DataType;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPrivateDataResponseDto {
    private DataType type;
    private String title;
    private String privateData;
    private String memo;
    private String privateDataId;
    private String url;
    private List<Long> roles;
}
