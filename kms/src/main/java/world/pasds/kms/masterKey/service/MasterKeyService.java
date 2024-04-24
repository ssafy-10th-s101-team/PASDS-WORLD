package world.pasds.kms.masterKey.service;

import com.github.benmanes.caffeine.cache.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.kms.masterKey.entity.MasterKey;
import world.pasds.kms.masterKey.repository.MasterKeyRepository;
import world.pasds.kms.util.AesUtil;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MasterKeyService {

    private final AesUtil aesUtil;
    private final MasterKeyRepository masterKeyRepository;
    public Cache<String, MasterKeyData> keyCache;
    private MasterKeyData prevMasterKey;

    public MasterKeyService(AesUtil aesUtil, MasterKeyRepository masterKeyRepository) {
        this.aesUtil = aesUtil;
        this.masterKeyRepository = masterKeyRepository;
    }

    @Transactional
    @PostConstruct
    public void init(){
        System.out.println("init 시작");
        //자동으로 만료하고 갱신하는 캐시 생성
        keyCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .scheduler(Scheduler.systemScheduler())
                .removalListener(new RemovalListener<String, MasterKeyData>() {
                    @Override
                    public void onRemoval(String key, MasterKeyData value, RemovalCause cause) {
                        log.info("제거 로직 발~동!!");
                        if (cause.wasEvicted()) { //자동 제거로 인해 발생했는지 확인
                            prevMasterKey = value; // 만료된 키 값을 prevMasterKey에 저장
                            keyCache.put(key, generateNewMasterKey()); // 새 키를 캐시에 저장
                            log.info("새로운 키 저장 완료~! : " + keyCache.getIfPresent("curMasterKey"));
                        }
                    }
                })
                .build();

        //초기 curMasterKey값 생성 및 저장
        keyCache.put("curMasterKey", generateNewMasterKey());

        System.out.println("제대로 초기화되었나확인. 키값 : "+keyCache.getIfPresent("curMasterKey"));
    }


    //만료 시 새로운 Master 값 생성.
    public MasterKeyData generateNewMasterKey() {
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

    public static class MasterKeyData{
        public byte[] value;
        public byte[] iv;

        public MasterKeyData(byte[] value, byte[] iv){
            this.value = value;
            this.iv = iv;
        }
    }

}
