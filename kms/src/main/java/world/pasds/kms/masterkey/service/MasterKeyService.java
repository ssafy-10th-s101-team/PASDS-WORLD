package world.pasds.kms.masterkey.service;

import com.github.benmanes.caffeine.cache.*;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import world.pasds.kms.datakey.model.MasterKeyData;
import world.pasds.kms.masterkey.entity.MasterKey;
import world.pasds.kms.masterkey.repository.MasterKeyRepository;
import world.pasds.kms.util.AesUtil;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MasterKeyService {

    @Value("${main-server.url}")
    private String serverUrl;
    private final AesUtil aesUtil;
    private final MasterKeyRepository masterKeyRepository;
    private final RestTemplate restTemplate;
    public Cache<String, MasterKeyData> keyCache;
    @Getter
    private MasterKeyData prevMasterKey;

    public MasterKeyService(AesUtil aesUtil, MasterKeyRepository masterKeyRepository, RestTemplate restTemplate) {
        this.aesUtil = aesUtil;
        this.masterKeyRepository = masterKeyRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    @PostConstruct
    public void init(){
        //자동으로 만료하고 갱신하는 캐시 생성
        keyCache = Caffeine.newBuilder()
                .expireAfterWrite(90, TimeUnit.DAYS)
                .scheduler(Scheduler.systemScheduler())
                .removalListener(new RemovalListener<String, MasterKeyData>() {
                    @Override
                    public void onRemoval(String key, MasterKeyData value, RemovalCause cause) {
                        if (cause.wasEvicted()) { //자동 제거로 인해 발생했는지 확인
                            prevMasterKey = value; // 만료된 키 값을 prevMasterKey에 저장
                            keyCache.put(key, generateNewMasterKey()); // 새 키를 캐시에 저장
                            log.info("Master Key Changed");

                            //마스터 키 변경을 메인 서버에 알림.
                            restTemplate.postForObject(serverUrl,null,Void.class);
                            log.info("Notify Master Key Change to Main Server");
                        }
                    }
                })
                .build();

        //초기 curMasterKey값 생성 및 저장
        keyCache.put("curMasterKey", generateNewMasterKey());
    }

    public MasterKeyData getCurMasterKey(){

        return keyCache.getIfPresent("curMasterKey");
    }

    //만료 시 새로운 Master 값 생성.
    private MasterKeyData generateNewMasterKey() {
        // 실제 키 생성
        byte[] key = aesUtil.keyGenerator();
        byte[] iv = aesUtil.IVGenerator();

        // db 저장
        MasterKey masterKey = new MasterKey();
        masterKey.setValue(key);
        masterKey.setIv(iv);
        masterKeyRepository.save(masterKey);

        return new MasterKeyData(key,iv);
    }


}
