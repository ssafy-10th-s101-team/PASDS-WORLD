package world.pasds.back.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponseDto {
    // TODO: TOTP key 반환
    private String tmp;
}
