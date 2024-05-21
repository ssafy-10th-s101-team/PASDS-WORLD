package world.pasds.back.member.dto.request;

import lombok.Getter;

@Getter
public class ResetPasswordRequestDto {
    private String password;
    private String confirmPassword;
}
