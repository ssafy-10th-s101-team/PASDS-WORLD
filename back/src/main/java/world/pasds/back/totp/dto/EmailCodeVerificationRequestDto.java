package world.pasds.back.totp.dto;

import lombok.Getter;

@Getter
public class EmailCodeVerificationRequestDto {
	private String email;
	private String otpCode;
}
