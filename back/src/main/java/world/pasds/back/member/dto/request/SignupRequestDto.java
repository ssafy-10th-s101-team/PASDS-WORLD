package world.pasds.back.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
    private String confirmPassword;
    private String nickname;
}
