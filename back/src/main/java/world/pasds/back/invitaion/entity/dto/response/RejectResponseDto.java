package world.pasds.back.invitaion.entity.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RejectResponseDto {
    boolean isExpired;
}