package world.pasds.back.totp.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.totp.dto.EmailCodeVerificationRequestDto;
import world.pasds.back.totp.dto.EmailSendRequestDto;
import world.pasds.back.totp.dto.TotpCodeVerificationRequestDto;
import world.pasds.back.totp.service.TotpService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api/totp")
@Slf4j
public class TotpController {

	private final TotpService totpService;

	@GetMapping("/share-key")
	public ResponseEntity<?> generateSecretKey() {
		return ResponseEntity.ok()
			.contentType(MediaType.IMAGE_PNG)
			.body(totpService.generateSecretKeyQR(1L));
	}

	@PostMapping("/re-share-key")
	public ResponseEntity<?> reGenerateSecretKey(@RequestBody EmailCodeVerificationRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
		totpService.verificationEmailCode(requestDto.getEmail(), requestDto.getOtpCode());
		return ResponseEntity.ok()
			.contentType(MediaType.IMAGE_PNG)
			.body(totpService.generateSecretKeyQR(userDetails.getMemberId()));
	}


	@PostMapping("/verification-totp-code")
	public ResponseEntity<?> verificationTotpCode(@RequestBody TotpCodeVerificationRequestDto requestDto) {
		totpService.verificationTotpCode(1L, requestDto.getTotpCode());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/email-verification-requests")
	public ResponseEntity<?> sendCodeToEmail(@RequestBody EmailSendRequestDto requestDto) {
		totpService.sendCodeToEmail(requestDto.getEmail());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/verification-email-code")
	public ResponseEntity<?> verificationEmailCode(@RequestBody EmailCodeVerificationRequestDto requestDto) {
		totpService.verificationEmailCode(requestDto.getEmail(), requestDto.getOtpCode());
		return ResponseEntity.ok().build();
	}

}
