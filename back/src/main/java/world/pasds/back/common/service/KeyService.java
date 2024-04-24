package world.pasds.back.common.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import world.pasds.back.common.dto.KmsDecryptionKeysRequestDto;
import world.pasds.back.common.dto.KmsDecryptionKeysResponseDto;
import world.pasds.back.common.dto.KmsEncryptionKeysResponseDto;
import world.pasds.back.common.util.AesUtil;
import world.pasds.back.totp.repository.TotpRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class KeyService {

	private final AesUtil aesUtil;
	private final RestTemplate restTemplate;
	private final String kmsUrl = "http://--domain--/kms/api";

	public KmsEncryptionKeysResponseDto generateKeys() {
		ResponseEntity<KmsEncryptionKeysResponseDto> response = restTemplate
			.getForEntity(kmsUrl+"/generate-key", KmsEncryptionKeysResponseDto.class);
		return response.getBody();
	}

	public KmsDecryptionKeysResponseDto getKeys(KmsDecryptionKeysRequestDto requestDto) {
		ResponseEntity<KmsDecryptionKeysResponseDto> response = restTemplate
			.postForEntity(kmsUrl+"/get-key", requestDto, KmsDecryptionKeysResponseDto.class);
		return response.getBody();
	}

	public String decryptSecret(byte[] secret, byte[] dataKey, byte[] ivKey) throws
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		return aesUtil.decrypt(secret, dataKey, ivKey);
	}

	public byte[] encryptSecret(String secret, byte[] dataKey, byte[] ivKey) throws
		InvalidAlgorithmParameterException,
		NoSuchPaddingException,
		IllegalBlockSizeException,
		NoSuchAlgorithmException,
		BadPaddingException,
		InvalidKeyException {
		return aesUtil.encrypt(secret, dataKey, ivKey);
	}


}
