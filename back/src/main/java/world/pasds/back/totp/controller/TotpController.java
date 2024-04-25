package world.pasds.back.totp.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;

import lombok.RequiredArgsConstructor;
import world.pasds.back.totp.service.TotpService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/api/totp")
public class TotpController {

	private final TotpService totpService;

	@GetMapping("/share-key")
	public ResponseEntity<?> generateSecretKey() throws
		IOException,
		WriterException,
		NoSuchAlgorithmException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		BadPaddingException,
		InvalidKeyException {
		return ResponseEntity.ok()
			.contentType(MediaType.IMAGE_PNG)
			.body(totpService.generateSecretKeyQR());
	}

	@GetMapping("/re-share-key")
	public ResponseEntity<?> reGenerateSecretKey() throws
		IOException,
		NoSuchAlgorithmException,
		WriterException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		BadPaddingException,
		InvalidKeyException {
		// todo 이메일 인증 otp 검증
		return ResponseEntity.ok()
			.contentType(MediaType.IMAGE_PNG)
			.body(totpService.generateSecretKeyQR());
	}


	@GetMapping("/validate-code")
	public ResponseEntity<?> validateTotpCode(
		@RequestBody String totpCode) throws
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		totpService.validateTotpCode(totpCode);
		return new ResponseEntity<>(HttpStatus.OK);
	}


}
