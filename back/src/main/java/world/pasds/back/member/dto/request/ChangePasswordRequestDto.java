package world.pasds.back.member.dto.request;

import lombok.Getter;

@Getter
public class ChangePasswordRequestDto {
	private String prevPassword;
	private String password;
	private String confirmPassword;
}
