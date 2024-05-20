package world.pasds.back.privateData.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeletePrivateDataRequestDto {
    private Long teamId;
    private Long privateDataId;
}
