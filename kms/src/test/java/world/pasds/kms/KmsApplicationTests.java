package world.pasds.kms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import world.pasds.kms.util.AesUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class KmsApplicationTests {

	@Test
	void aesUtilTest() throws Exception{
		AesUtil aesUtil = new AesUtil();
		byte[] key = aesUtil.keyGenerator();
		byte[] iv = aesUtil.IVGenerator();

		String originText= "Hello World!";

		//암호화 수행
		byte[] encrypted = aesUtil.encrypt(originText.getBytes(),key,iv);
		assertNotNull(encrypted, "Encrypted text should not be null");

		// 복호화 수행
		String decryptedText = new String(aesUtil.decrypt(encrypted, key, iv), StandardCharsets.UTF_8);
		assertNotNull(decryptedText, "Decrypted text should not be null");

		//원본텍스트와 복호화된 텍스트 비교
		assertEquals(originText, decryptedText, "Decrypted text should match the original");
	}

}
