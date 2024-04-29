package world.pasds.back.organization.entity.dto.request;

import lombok.Getter;

@Getter
public class AssignNewHeaderRequestDto {
    private Long organizationId;
    private Long newHeaderId;
}
