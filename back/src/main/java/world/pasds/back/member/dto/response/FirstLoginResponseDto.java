package world.pasds.back.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FirstLoginResponseDto {
    private String nickname;
}
