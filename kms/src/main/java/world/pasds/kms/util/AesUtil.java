package world.pasds.kms.util;

import org.springframework.stereotype.Component;
import world.pasds.kms.common.exception.BusinessException;
import world.pasds.kms.common.exception.ExceptionCode;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


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
    public byte[] encrypt(byte[] plainBytes, byte[] key, byte[] iv) {
        try{
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            //암호화 Cipher 초기화
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            //암호화 및 리턴
            return  cipher.doFinal(plainBytes);
        } catch(NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            throw new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }


    }

    //복호화
    public byte[] decrypt(byte[] cipherBytes, byte[] key, byte[] iv) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");

            //암호화 Cipher 초기화
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            //복호화 및 리턴
            return cipher.doFinal(cipherBytes);
        }
        catch(NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            throw new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }
}

