package world.pasds.back.organization.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssignNewHeaderRequestDto {
    private Long organizationId;
    private Long newHeaderId;
}
