package world.pasds.back.privateData.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePrivateDataRoleRequestDto {
    private Long teamId;
    private Long privateDataId;
    private List<Long> roleId;

}
