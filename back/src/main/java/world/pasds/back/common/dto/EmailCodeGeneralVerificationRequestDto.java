package world.pasds.back.common.dto;

import lombok.Getter;

@Getter
public class EmailCodeGeneralVerificationRequestDto {
	private String email;
	private String otpCode;
}
