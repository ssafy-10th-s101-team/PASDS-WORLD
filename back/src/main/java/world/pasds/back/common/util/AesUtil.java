package world.pasds.back.common.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;

public class AesUtil {

	//키 생성
	public byte[] keyGenerator() {
		try {
			// AES 알고리즘 확인
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

			// 랜덤 시드 생성.
			SecureRandom random = new SecureRandom();

			//256비트 키 생성
			keyGenerator.init(256, random);

			//키 생성
			SecretKey secretKey = keyGenerator.generateKey();

			//byte[]로 변환후 리턴
			return secretKey.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			throw new BusinessException(ExceptionCode.KEY_ERROR);
		}
	}

	//암호화
	public byte[] encrypt(String plain, byte[] key, byte[] iv) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

			//암호화 Cipher 초기화
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

			//암호화할 문자열
			byte[] plainBytes = plain.getBytes();

			//암호화
			return cipher.doFinal(plainBytes);

		} catch (NoSuchPaddingException |
				 NoSuchAlgorithmException |
				 InvalidAlgorithmParameterException |
				 IllegalBlockSizeException |
				 BadPaddingException |
				 InvalidKeyException e) {
			throw new BusinessException(ExceptionCode.KEY_ERROR);
		}
	}

	//복호화
	public String decrypt(byte[] cipherBytes, byte[] key, byte[] iv) {
		try {
			SecretKey secretKey = new SecretKeySpec(key,"AES");

			//암호화 Cipher 초기화
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

			//복호화
			byte[] plainBytes = cipher.doFinal(cipherBytes);

			// String 형태로 바꾼 후 리턴
			return new String(plainBytes, StandardCharsets.UTF_8);
		} catch (NoSuchPaddingException |
				 NoSuchAlgorithmException |
				 InvalidAlgorithmParameterException |
				 IllegalBlockSizeException |
				 BadPaddingException |
				 InvalidKeyException e) {
			throw new BusinessException(ExceptionCode.KEY_ERROR);
		}
	}
}