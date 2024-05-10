package world.pasds.back.totp.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.totp.dto.EmailCodeKeyVerificationRequestDto;
import world.pasds.back.totp.service.TotpService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api/totp")
@Slf4j
public class TotpController {

	private final TotpService totpService;

	@PostMapping("/re-share-key")
	public ResponseEntity<?> reGenerateSecretKey(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody EmailCodeKeyVerificationRequestDto requestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
		totpService.verificationEmailCode(request,response,userDetails, requestDto.getOtpCode());
		return ResponseEntity.ok()
			.contentType(MediaType.IMAGE_PNG)
			.body(totpService.generateSecretKeyQR(userDetails.getMemberId()));
	}

	@PostMapping("/verification-email-code")
	public ResponseEntity<?> verificationEmailCode(HttpServletRequest request, HttpServletResponse response,
		@RequestBody EmailCodeKeyVerificationRequestDto requestDto,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		totpService.verificationEmailCode(request, response, userDetails, requestDto.getOtpCode());
		return ResponseEntity.ok().build();
	}

}
