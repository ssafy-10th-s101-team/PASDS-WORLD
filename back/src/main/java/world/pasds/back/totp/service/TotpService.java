package world.pasds.back.totp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import world.pasds.back.common.service.KeyService;
import world.pasds.back.common.util.AesUtil;
import world.pasds.back.common.dto.KmsDecryptionKeysRequestDto;
import world.pasds.back.common.dto.KmsDecryptionKeysResponseDto;
import world.pasds.back.common.dto.KmsEncryptionKeysResponseDto;
import world.pasds.back.totp.repository.TotpRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TotpService {

	private final TotpRepository totpRepository;
	private final KeyService keyService;
	private final AesUtil aesUtil;

	public byte[] generateSecretKeyQR() throws
		WriterException,
		IOException,
		NoSuchAlgorithmException,
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		BadPaddingException,
		InvalidKeyException {
		Long memberId = 1L;

		// qr 정보
		int width = 200;
		int height = 200;

		// totp key 생성
		byte[] totpKey = aesUtil.keyGenerator();
		String base64EncodedTotpKey = Base64.getEncoder().encodeToString(totpKey);


		KmsEncryptionKeysResponseDto totpEncryptionKeys = keyService.generateKeys();
		byte[] encryptedTotpKey = keyService.encryptSecret(base64EncodedTotpKey,
			Base64.getDecoder().decode(totpEncryptionKeys.getDataKey()),
			Base64.getDecoder().decode(totpEncryptionKeys.getIvKey()));
		// todo db에 암호화 한 totpKey, dataKey, ivKey 저장 !!

		// QR Code - BitMatrix: qr code 정보 생성
		BitMatrix bitMatrix = new MultiFormatWriter()
			.encode(base64EncodedTotpKey, BarcodeFormat.QR_CODE, width, height);

		// QR Code - Image 생성: 일회성 stream
		// 일회성 아니면 File
		ByteArrayOutputStream qr = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", qr);
		return qr.toByteArray();
	}


	public void validateTotpCode(String inputTotpCode) throws
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		Long memberId = 1L;

		String totpKey = getDecryptedTotpKey(memberId);
		String totpCode = generateTotpCode(totpKey, LocalDateTime.now());

		// 생성한 totp code 와 받은 totp code 가 같은 지 확인 & 검증
		if (!inputTotpCode.equals(totpCode)) {
			throw new RuntimeException("2차 인증에 실패했습니다.");
		}
	}

	private String getDecryptedTotpKey(Long memberId) throws
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {


		// todo exception 처리 수정
		byte[] encryptedTotpDataKey = totpRepository.findEncryptedTotpDataKeyByMemberId(memberId)
			.orElseThrow(() -> new RuntimeException("totp data key를 찾을 수 없습니다."));

		byte[] encryptedTotpIvKey = totpRepository.findEncryptedTotpIvKeyByMemberId(memberId)
			.orElseThrow(() -> new RuntimeException("totp iv key를 찾을 수 없습니다."));

		KmsDecryptionKeysRequestDto kmsRequest = KmsDecryptionKeysRequestDto
			.builder()
			.encryptedDataKey(Base64.getEncoder().encodeToString(encryptedTotpDataKey))
			.encryptedIvKey(Base64.getEncoder().encodeToString(encryptedTotpIvKey))
			.build();

		// KMS 에 totp key 복호화를 위한 data key, iv key 요청
		KmsDecryptionKeysResponseDto totpDecryptionKeys = keyService.getKeys(kmsRequest);

		byte[] encryptedTotpKeyByMemberId = totpRepository.findEncryptedTotpKeyByMemberId(memberId)
			.orElseThrow(() -> new RuntimeException("totp key를 찾을 수 없습니다."));

		return keyService.decryptSecret(encryptedTotpKeyByMemberId,
			Base64.getDecoder().decode(totpDecryptionKeys.getDataKey()),
			Base64.getDecoder().decode(totpDecryptionKeys.getIvKey()));
	}

	private String generateTotpCode(String totpKey, LocalDateTime serverTime) {
		// T = (UT - T0) / (time_step)
		// UT : 1970-01-01 이후 경과된 시간
		// T0 : 시스템 파라미터 (OTP가 갱신된 횟수를 사용)
		// time_step : 유효시간
		// 유효시간을 나눈 몫을 사용하면 동일한 유효시간안에는 같은 값을 얻을 수 있음
		Long time = ((serverTime.toEpochSecond(ZoneOffset.UTC)) / 60L);
		// Convert time step to a byte array
		byte[] timeData = ByteBuffer.allocate(8).putLong(time).array();
		return hotp(totpKey, timeData);
	}

	private String hotp(String totpKey, byte[] time) {
		return "구현 중";
	}

	private String hmacAndBase64(String totpKey, String serverTime) throws
		NoSuchAlgorithmException,
		InvalidKeyException {

		//1. SecretKeySpec 클래스를 사용한 키 생성
		SecretKeySpec secretKey = new SecretKeySpec(totpKey.getBytes(), "HmacSHA256");

		//2. 지정된  MAC 알고리즘을 구현하는 Mac 객체를 작성합니다.
		Mac hasher = Mac.getInstance("HmacSHA256");

		//3. 키를 사용해 이 Mac 객체를 초기화
		hasher.init(secretKey);

		//3. 암호화 하려는 데이터의 바이트의 배열을 처리해 MAC 조작을 종료
		byte[] hash = hasher.doFinal(serverTime.getBytes());

		//4. Base 64 Encode to String
		return Base64.getEncoder().encodeToString(hash);
	}

}
