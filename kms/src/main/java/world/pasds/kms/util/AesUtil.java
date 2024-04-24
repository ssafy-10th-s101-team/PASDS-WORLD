package world.pasds.kms.util;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;


@Component
public class AesUtil {

    //키 생성
    public byte[] keyGenerator(){

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
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
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

