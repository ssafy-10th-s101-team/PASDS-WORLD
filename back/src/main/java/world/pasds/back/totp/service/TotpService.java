package world.pasds.back.totp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
			Base64.getDecoder().decode(totpEncryptionKeys.getIv()));
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
			.encryptedIv(Base64.getEncoder().encodeToString(encryptedTotpIvKey))
			.build();

		// KMS 에 totp key 복호화를 위한 data key, iv key 요청
		KmsDecryptionKeysResponseDto totpDecryptionKeys = keyService.getKeys(kmsRequest);

		byte[] encryptedTotpKeyByMemberId = totpRepository.findEncryptedTotpKeyByMemberId(memberId)
			.orElseThrow(() -> new RuntimeException("totp key를 찾을 수 없습니다."));

		return keyService.decryptSecret(encryptedTotpKeyByMemberId,
			Base64.getDecoder().decode(totpDecryptionKeys.getDataKey()),
			Base64.getDecoder().decode(totpDecryptionKeys.getIv()));
	}

	private String generateTotpCode(String totpKey, LocalDateTime serverTime) throws
		NoSuchAlgorithmException,
		InvalidKeyException {
		// time = (UT - T0) / (time_step)
		// UT : 1970-01-01 이후 경과된 시간
		// T0 : 서버 시작 시간 (0L)
		// time_step : 유효시간 (30L; 30초)

		long time = (serverTime.toEpochSecond(ZoneOffset.UTC)) / 30L;
		// Convert time step to a byte array
		byte[] timeData = ByteBuffer.allocate(8).putLong(time).array();
		return String.format("%06d", hotp(totpKey, timeData));			// 6자리 숫자 코드
	}

	private int hotp(String totpKey, byte[] time) throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] hash = hmacAndBase64(totpKey, time);
		int offset = hash[hash.length - 1] & 0xf;
		int binary = (hash[offset] & 0x7f) << 24 | (hash[offset + 1] & 0xff) << 16 |
			(hash[offset + 2] & 0xff) << 8 | (hash[offset + 3] & 0xff);
		return binary % 100000;						// 6자리 숫자 코드
	}

	private byte[] hmacAndBase64(String totpKey, byte[] time) throws
		NoSuchAlgorithmException,
		InvalidKeyException {

		//1. SecretKeySpec 클래스를 사용한 키 생성
		SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(totpKey), "HmacSHA256");

		//2. 지정된  MAC 알고리즘을 구현하는 Mac 객체를 작성합니다.
		Mac mac = Mac.getInstance("HmacSHA256");

		//3. 키를 사용해 이 Mac 객체를 초기화
		mac.init(secretKey);

		//3. 암호화 하려는 데이터의 바이트의 배열을 처리해 MAC 조작을 종료
		return  mac.doFinal(time);
	}

}
