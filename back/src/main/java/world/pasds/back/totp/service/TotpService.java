package world.pasds.back.totp.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.dto.KmsDecryptionKeysResponseDto;
import world.pasds.back.common.dto.KmsEncryptionKeysResponseDto;
import world.pasds.back.common.dto.KmsKeyDto;
import world.pasds.back.common.dto.KmsReGenerationKeysResponseDto;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.common.service.EmailService;
import world.pasds.back.common.service.KeyService;
import world.pasds.back.common.util.AesUtil;
import world.pasds.back.member.entity.Member;
import world.pasds.back.member.repository.MemberRepository;
import world.pasds.back.totp.repository.TotpRepository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TotpService {

	private final MemberRepository memberRepository;
	private final TotpRepository totpRepository;
	private final KeyService keyService;
	private final EmailService emailService;
	private final AesUtil aesUtil;

	private static final String AUTH_CODE_PREFIX = "AuthCode ";
	@Value("${spring.mail.auth-code-expiration-millis}")
	private long authCodeExpirationMillis;

	public byte[] generateSecretKeyQR(Long memberId) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new BusinessException(ExceptionCode.MEMBER_NOT_FOUND));
		// totp key
		byte[] totpKey = aesUtil.keyGenerator();
		saveTotpKeySets(totpKey, member);

		// qr code
		return generateQRCode(Base64.getEncoder().encodeToString(totpKey));
	}

	private void saveTotpKeySets(byte[] totpKey, Member member) {
		// key 요청
		KmsEncryptionKeysResponseDto totpEncryptionKeys = keyService.generateKeys();
		byte[] encryptedTotpKey = keyService.encryptSecret(totpKey,
			Base64.getDecoder().decode(totpEncryptionKeys.getDataKey()),
			Base64.getDecoder().decode(totpEncryptionKeys.getIv()));

		// db에 암호화 한 totpKey, dataKey, iv 저장
		member.setEncryptedTotpKey(encryptedTotpKey);
		member.setEncryptedTotpDataKey(Base64.getDecoder().decode(totpEncryptionKeys.getDataKey()));
		member.setEncryptedTotpIvKey(Base64.getDecoder().decode(totpEncryptionKeys.getIv()));
		memberRepository.save(member);
	}

	private byte[] generateQRCode(String base64EncodedTotpKey) {
		try {
			// qr 정보
			int width = 200;
			int height = 200;
			// QR Code - BitMatrix: qr code 정보 생성
			BitMatrix bitMatrix = new MultiFormatWriter()
				.encode(base64EncodedTotpKey, BarcodeFormat.QR_CODE, width, height);
			// QR Code - Image 생성: 일회성 stream
			// 일회성 아니면 File
			ByteArrayOutputStream qr = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", qr);
			return qr.toByteArray();
		} catch (WriterException | IOException e) {
			throw new BusinessException(ExceptionCode.KEY_ERROR);
		}

	}

	public void validateTotpCode(Long memberId, String inputTotpCode) {

		byte[] totpKey = getDecryptedTotpKey(memberId);
		String totpCode = generateTotpCode(totpKey, LocalDateTime.now());

		// 생성한 totp code 와 받은 totp code 가 같은 지 확인 & 검증
		if (!inputTotpCode.equals(totpCode)) {
			throw new BusinessException(ExceptionCode.TOTP_CODE_NOT_SAME);
		}
	}

	private byte[] getDecryptedTotpKey(Long memberId) {

		byte[] encryptedTotpDataKey = totpRepository.findEncryptedTotpDataKeyByMemberId(memberId)
			.orElseThrow(() -> new BusinessException(ExceptionCode.KEY_ERROR));

		byte[] encryptedTotpIvKey = totpRepository.findEncryptedTotpIvKeyByMemberId(memberId)
			.orElseThrow(() -> new BusinessException(ExceptionCode.KEY_ERROR));

		KmsKeyDto kmsRequest = KmsKeyDto
			.builder()
			.encryptedDataKey(Base64.getEncoder().encodeToString(encryptedTotpDataKey))
			.encryptedIv(Base64.getEncoder().encodeToString(encryptedTotpIvKey))
			.build();

		// KMS 에 totp key 복호화를 위한 data key, iv 요청
		KmsDecryptionKeysResponseDto totpDecryptionKeys = keyService.getKeys(kmsRequest);

		byte[] encryptedTotpKeyByMemberId = totpRepository.findEncryptedTotpKeyByMemberId(memberId)
			.orElseThrow(() -> new BusinessException(ExceptionCode.KEY_ERROR));

		return keyService.decryptSecret(encryptedTotpKeyByMemberId,
			Base64.getDecoder().decode(totpDecryptionKeys.getDataKey()),
			Base64.getDecoder().decode(totpDecryptionKeys.getIv()));
	}

	private String generateTotpCode(byte[] totpKey, LocalDateTime serverTime) {
		// time = (UT - T0) / (time_step)
		// UT : 1970-01-01 이후 경과된 시간
		// T0 : 서버 시작 시간 (0L)
		// time_step : 유효시간 (30L; 30초)

		long time = (serverTime.toEpochSecond(ZoneOffset.UTC)) / 30L;
		// Convert time step to a byte array
		byte[] timeData = ByteBuffer.allocate(8).putLong(time).array();
		return String.format("%06d", hotp(totpKey, timeData));			// 6자리 숫자 코드
	}

	private int hotp(byte[] totpKey, byte[] time) {
		byte[] hash = hmacAndBase64(totpKey, time);
		int offset = hash[hash.length - 1] & 0xf;
		int binary = (hash[offset] & 0x7f) << 24 | (hash[offset + 1] & 0xff) << 16 |
			(hash[offset + 2] & 0xff) << 8 | (hash[offset + 3] & 0xff);
		return binary % 1000000;						// 6자리 숫자 코드
	}

	private byte[] hmacAndBase64(byte[] totpKey, byte[] time) {
		try {
			//1. SecretKeySpec 클래스를 사용한 키 생성
			SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(totpKey), "HmacSHA256");
			//2. 지정된  MAC 알고리즘을 구현하는 Mac 객체를 작성합니다.
			Mac mac = Mac.getInstance("HmacSHA256");
			// 키를 사용해 Mac 객체를 초기화
			mac.init(secretKey);
			//3. 키를 사용해 이 Mac 객체를 초기화
			mac.init(secretKey);
			//4. 암호화 하려는 데이터의 바이트의 배열을 처리해 MAC 조작을 종료
			return  mac.doFinal(time);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new BusinessException(ExceptionCode.TOTP_CODE_GENERATION_ERROR);
		}
	}

	public void validateEmailCode(String email, String authCode) {
		String redisAuthCode = emailService.getRedisAuthCode(AUTH_CODE_PREFIX + email);
		if (!emailService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode)){
			throw new BusinessException(ExceptionCode.EMAIL_CODE_NOT_SAME);
		};
	}

	public void sendCodeToEmail(String toEmail) {
		String subject = "[PASDSWORLD] 이메일 인증 코드입니다.";
		String authCode = createCode();
		// 인증 번호 전송
		emailService.sendMessage(toEmail, subject, authCode);
		// 이메일 인증 요청 시 인증 번호 Redis 에 저장 ( key = "AuthCode " + Email / value = AuthCode )
		emailService.setRedisAuthCode(AUTH_CODE_PREFIX + toEmail,
			authCode, Duration.ofMillis(authCodeExpirationMillis));
	}

	private String createCode() {
		int length = 8;
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(secureRandom.nextInt(10));
		}
		return sb.toString();
	}


	@Async
	@Transactional
	public void refreshByMasterKey(){

		//member 목록 가져오기..
		Long startId = 0L;
		Long endId = 1000L;
		while(true) {
			List<Member> members = memberRepository.findByIdBetween(startId, endId);
			if (!members.isEmpty()) {
				for(Member member : members){
					//members에서 encryptedTotpDataKey, encryptedTotpIvKey 가져오기.
					KmsKeyDto requestDto = KmsKeyDto.builder()
							.encryptedDataKey(Base64.getEncoder().encodeToString(member.getEncryptedTotpDataKey()))
							.encryptedIv(Base64.getEncoder().encodeToString(member.getEncryptedTotpIvKey()))
							.build();

					//data key 재암호화 요청.
					KmsKeyDto responseDto = keyService.reEncrypt(requestDto);

					//재암호화된 data key들 갱신
					member.setEncryptedTotpDataKey(Base64.getDecoder().decode(responseDto.getEncryptedDataKey()));
					member.setEncryptedTotpIvKey(Base64.getDecoder().decode(responseDto.getEncryptedIv()));
					memberRepository.save(member);

					//로그 찍기
					log.info("member {}'s TotpDataKey re-encrypted", member.getId());
				}
				startId = endId;
				endId += 1000L;
			} else {
				break;
			}
		}
	}

	@Async
	@Transactional
	public void rotateDataKey(){
		//member 목록 가져오기..
		Long startId = 0L;
		Long endId = 1000L;

		//멤버 풀스캔. 1000개씩 검색.
		while(true){
			List<Member> members = memberRepository.findByIdBetween(startId, endId);
			if (!members.isEmpty()) {
				for(Member member : members){

					//만료여부확인
					LocalDateTime expiredAt = member.getExpiredAt();

					if(expiredAt.isAfter(LocalDateTime.now())) continue;

					//만료시간이 지났으면 갱신로직 시작

					//members에서 encryptedTotpDataKey, encryptedTotpIvKey 가져오기.
					KmsKeyDto requestDto = KmsKeyDto.builder()
							.encryptedDataKey(Base64.getEncoder().encodeToString(member.getEncryptedTotpDataKey()))
							.encryptedIv(Base64.getEncoder().encodeToString(member.getEncryptedTotpIvKey()))
							.build();

					//기존 데이터 키 복호화 및 재발급 요청
					KmsReGenerationKeysResponseDto responseDto = keyService.reGenerateKey(requestDto);

					//totp key 복호화
					byte[] totpKey = keyService.decryptSecret(member.getEncryptedTotpKey(),
							Base64.getDecoder().decode(responseDto.getOldDataKey()),
							Base64.getDecoder().decode(responseDto.getOldIv()));

					//재암호화
					byte[] encryptedTotpKey = keyService.encryptSecret(totpKey,
							Base64.getDecoder().decode(responseDto.getNewDataKey()),
							Base64.getDecoder().decode(responseDto.getNewIv()));

					//재암호화된 data key들 갱신
					member.setEncryptedTotpKey(encryptedTotpKey);
					member.setEncryptedTotpDataKey(Base64.getDecoder().decode(responseDto.getEncryptedNewDataKey()));
					member.setEncryptedTotpIvKey(Base64.getDecoder().decode(responseDto.getEncryptedNewIv()));
					memberRepository.save(member);

					//로그 찍기
					log.info("member {}'s TotpDataKey re-generated", member.getId());
				}
				startId = endId;
				endId += 1000L;
			} else {
				break;
			}
		}

	}
}
