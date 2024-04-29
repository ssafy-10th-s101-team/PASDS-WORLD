package world.pasds.back.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import world.pasds.back.common.dto.KmsDecryptionKeysRequestDto;
import world.pasds.back.common.dto.KmsDecryptionKeysResponseDto;
import world.pasds.back.common.dto.KmsEncryptionKeysResponseDto;
import world.pasds.back.common.dto.KmsReEncryptionKeysDto;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.common.util.AesUtil;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class KeyService {
    private final AesUtil aesUtil;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String KMS_URL = "http://k10s101.p.ssafy.io:8081/kms/api";

    public KmsEncryptionKeysResponseDto generateKeys() {
        ResponseEntity<KmsEncryptionKeysResponseDto> response = restTemplate
                .getForEntity(KMS_URL + "/generate-key", KmsEncryptionKeysResponseDto.class);
        return response.getBody();
    }

    public KmsDecryptionKeysResponseDto getKeys(KmsDecryptionKeysRequestDto requestDto) {
        ResponseEntity<KmsDecryptionKeysResponseDto> response = restTemplate
                .postForEntity(KMS_URL + "/get-key", requestDto, KmsDecryptionKeysResponseDto.class);
        return response.getBody();
    }

    public byte[] decryptSecret(byte[] secret, byte[] dataKey, byte[] iv) {
        return aesUtil.decrypt(secret, dataKey, iv);
    }

    public byte[] encryptSecret(byte[] secret, byte[] dataKey, byte[] iv) {
        return aesUtil.encrypt(secret, dataKey, iv);
    }

    public String getJwtSecretKey() {
        //레디스에서 가져온다.
        String jwtSecretKey = (String) redisTemplate.opsForValue().get("curJwtSecretKey");

        //레디스에 없다면 최초거나 갱신 요청임.
        if (jwtSecretKey == null) {
            //kms에 요청한다.
            jwtSecretKey = restTemplate.getForEntity(KMS_URL + "/jwt-secret-key/generate-key", String.class).getBody();

            //가져왔는데도 null 이면 예외 발생
            if (jwtSecretKey == null) throw new BusinessException(ExceptionCode.KEY_ERROR);

            //redis에 저장한다. 만료시간을 90일로 설정한다.
            int REDIS_EXPIRE_SECOND = 20; //60 * 60 * 24 * 90;
            redisTemplate.opsForValue().set("curJwtSecretKey", jwtSecretKey, REDIS_EXPIRE_SECOND, TimeUnit.SECONDS);
            //만료시간 없이 백업 키도 저장. 만료시 prevJwtSecretKey키 설정을 위해
            redisTemplate.opsForValue().set("backUpJwtSecretKey", jwtSecretKey);

            log.info("New JwtSecretKey generated");
        }
        //리턴
        return jwtSecretKey;
    }

    public String getPrevJwtSecretKey() {
        return (String) redisTemplate.opsForValue().get("prevJwtSecretKey");
    }

    public KmsReEncryptionKeysDto reEncrypt(KmsReEncryptionKeysDto requestDto){
        ResponseEntity<KmsReEncryptionKeysDto> response = restTemplate
                .postForEntity(KMS_URL + "/reencrypt-key", requestDto, KmsReEncryptionKeysDto.class);
        return response.getBody();
    }
}
