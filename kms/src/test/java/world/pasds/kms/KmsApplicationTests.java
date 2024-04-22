package world.pasds.kms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import world.pasds.kms.util.AesUtil;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class KmsApplicationTests {

	@Test
	void aesUtilTest() throws Exception{
		System.out.println("테스트시자아아아앙아아악");
		AesUtil aesUtil = new AesUtil();
		byte[] key = aesUtil.keyGenerator();
		byte[] iv = aesUtil.IVGenerator();

		String originText= "Hello World!";

		//암호화 수행
		byte[] encrypted = aesUtil.encrypt(originText,key,iv);
		assertNotNull(encrypted, "Encrypted text should not be null");

		// 복호화 수행
		String decryptedText = aesUtil.decrypt(encrypted, key, iv);
		assertNotNull(decryptedText, "Decrypted text should not be null");
		System.out.println("Decrpyted Text : "+ decryptedText);


		//원본텍스트와 복호화된 텍스트 비교
		assertEquals(originText, decryptedText, "Decrypted text should match the original");
	}

}
