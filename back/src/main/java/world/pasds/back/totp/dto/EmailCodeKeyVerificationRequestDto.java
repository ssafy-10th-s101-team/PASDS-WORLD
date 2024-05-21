package world.pasds.back.totp.dto;

import lombok.Getter;

@Getter
public class EmailCodeKeyVerificationRequestDto {
	private String otpCode;
}
