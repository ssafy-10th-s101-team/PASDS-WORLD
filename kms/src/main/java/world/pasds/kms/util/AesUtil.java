package world.pasds.kms.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;

public class AesUtil {

    //키 생성
    public byte[] keyGenerator() throws NoSuchAlgorithmException {
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
    }


    //IV 생성
    public byte[] IVGenerator(){
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    //암호화
    public byte[] encrypt(String plain, byte[] key, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        //암호화 Cipher 초기화
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

        //암호화할 문자열
        byte[] plainBytes = plain.getBytes();

        //암호화
        byte[] cipherBytes = cipher.doFinal(plainBytes);

        return cipherBytes;
    }

    //복호화
    public String decrypt(byte[] cipherBytes, byte[] key, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKey secretKey = new SecretKeySpec(key,"AES");

        //암호화 Cipher 초기화
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

        //복호화
        byte[] plainBytes = cipher.doFinal(cipherBytes);

        //String형태로 바꾼 후 리턴
        return new String(plainBytes, StandardCharsets.UTF_8);
    }
}


/*
Secret Key -> 인코드된 SecretKey -> 저장.
byte[] IV -> 저장.
비밀 String -> byte[]로 변경 -> 암호화 -> 암호화된 byte로 저장.
비밀 String -> byte[]로변경 -> 암호화 -> 암호화된 byte로 저장.


암호화에 쓰이는 모든 값들이 byte[]로 쓰인다.
굳이 String으로 한번더 변환할 필요 X.

실제 보여줄때만 (유저와 닿는 곳에서만) String으로 변환한다.

결론 : mysql에 byte[]로 저장.

 */

/*
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AesEncryptionUtil {

    public static void main(String[] args) {
        try {
            // 키 생성
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();

            // 초기화 벡터(IV) 생성
            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // 암호화 Cipher 초기화
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            // 암호화할 문자열
            String inputString = "1234";
            byte[] inputBytes = inputString.getBytes();

            // 데이터 암호화
            byte[] encryptedBytes = cipher.doFinal(inputBytes);

            // 결과 출력 (Base64 인코딩)
            String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);
            String ivBase64 = Base64.getEncoder().encodeToString(iv);

            System.out.println("Encrypted (Base64): " + encryptedBase64);
            System.out.println("IV (Base64): " + ivBase64);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

 */