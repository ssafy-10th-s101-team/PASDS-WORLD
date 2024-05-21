package world.pasds.kms.util;

import org.springframework.stereotype.Component;
import world.pasds.kms.common.exception.BusinessException;
import world.pasds.kms.common.exception.ExceptionCode;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class HmacUtil {
    public byte[] keyGenerator(){

        try {
            // KeyGenerator 인스턴스 생성, HMAC-SHA256 키 생성기 지정
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");

            // 안전한 난수 생성기 초기화
            SecureRandom random = new SecureRandom();
            keyGen.init(256, random); // 256 비트 키를 사용

            // 키 생성
            SecretKey secretKey = keyGen.generateKey();

            // 바이트 배열로 키를 추출 후 리턴
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(ExceptionCode.KEY_GENERATE_FAIL);
        }

    }
}
