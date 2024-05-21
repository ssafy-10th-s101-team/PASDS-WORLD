package world.pasds.back.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import world.pasds.back.common.service.EmailService;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.common.dto.EmailCodeGeneralVerificationRequestDto;
import world.pasds.back.common.dto.EmailSendGeneralRequestDto;
import world.pasds.back.totp.service.TotpService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api/email")
@Slf4j
public class EmailController {

	private final EmailService emailService;

	@PostMapping("/signup-verification-requests")
	public ResponseEntity<?> sendCodeToEmailForSignup(@RequestBody EmailSendGeneralRequestDto requestDto) {
		emailService.sendCodeToEmailForSignup(requestDto.getEmail());
		return ResponseEntity.ok().build();
	}
	@PostMapping("/password-verification-requests")
	public ResponseEntity<?> sendCodeToEmailForResetPassword(@RequestBody EmailSendGeneralRequestDto requestDto) {
		emailService.sendCodeToEmailForResetPassword(requestDto.getEmail());
		return ResponseEntity.ok().build();
	}
	@GetMapping("/totp-key-verification-requests")
	public ResponseEntity<?> sendCodeToEmailForReShareTotpKey(@AuthenticationPrincipal CustomUserDetails userDetails) {
		emailService.sendCodeToEmailForReShareTotpKey(userDetails);
		return ResponseEntity.ok().build();
	}


	// 일반적인 이메일 인증 코드 검증 (TEMP 토큰 없는 것들, 이메일 들어오는 것들)
	@PostMapping("/verification-email-code")
	public ResponseEntity<?> verificationEmailCode(
		HttpServletRequest request, HttpServletResponse response,
		@RequestBody EmailCodeGeneralVerificationRequestDto requestDto) {
		emailService.verificationEmailCode(request,response,requestDto.getEmail(), requestDto.getOtpCode());
		return ResponseEntity.ok().build();
	}

}