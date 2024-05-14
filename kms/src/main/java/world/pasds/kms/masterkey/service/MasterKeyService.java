package world.pasds.kms.masterkey.service;

import com.github.benmanes.caffeine.cache.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import world.pasds.kms.common.exception.BusinessException;
import world.pasds.kms.common.exception.ExceptionCode;
import world.pasds.kms.datakey.model.MasterKeyData;
import world.pasds.kms.masterkey.entity.MasterKey;
import world.pasds.kms.masterkey.repository.MasterKeyRepository;
import world.pasds.kms.util.AesUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MasterKeyService {

    @Value("${main-server.url}")
    private String serverUrl;

    private final long EXPIRE_TIME = 60L * 60L * 24L * 7L; //현재는 일주일 60L * 60L * 24L * 90L; //90일을 초단위로
    private final AesUtil aesUtil;
    private final MasterKeyRepository masterKeyRepository;
    private final RestTemplate restTemplate;
    public Cache<String, MasterKeyData> keyCache;

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
                .expireAfter(new Expiry<String, MasterKeyData>() {
                    @Override
                    public long expireAfterCreate(String key, MasterKeyData value, long currentTime) {
                        // 객체 생성 시 만료 시간을 설정
                        return TimeUnit.SECONDS.toNanos(value.getExpirySeconds());
                    }

                    @Override
                    public long expireAfterUpdate(String key, MasterKeyData value, long currentTime, long currentDuration) {
                        // 객체 갱신 시 만료 시간을 다시 계산
                        return TimeUnit.SECONDS.toNanos(value.getExpirySeconds());
                    }

                    @Override
                    public long expireAfterRead(String key, MasterKeyData value, long currentTime, long currentDuration) {
                        // 객체 조회 시 만료 시간을 유지
                        return currentDuration;
                    }
                })
                .scheduler(Scheduler.systemScheduler())
                .removalListener(new RemovalListener<String, MasterKeyData>() {
                    @Override
                    public void onRemoval(String key, MasterKeyData value, RemovalCause cause) {
                        if (cause.wasEvicted()) { //자동 제거로 인해 발생했는지 확인

                            //이전 키면 아무일도 하지 않는다.
                            if(key.equals("prevMasterKey")) {
                                log.info("prev Master Key Expired. key ID : {}", value.getId());
                                return;
                            }

                            log.info("current Master Key Expired. key ID : {}", value.getId());

                            //장애나 회전시 데이터 잘못된 복호화 예방을 위한 추가 만료시간 설정.
                            MasterKey expiredMasterKey = masterKeyRepository.findMasterKeyById(value.getId());
                            expiredMasterKey.setExpiredAt(LocalDateTime.now().plusSeconds(EXPIRE_TIME/2));
                            masterKeyRepository.save(expiredMasterKey);

                            //만료된 키를 prevMasterKey로 저장.
                            value.setExpirySeconds(value.getExpirySeconds()/2);
                            keyCache.put("prevMasterKey", value);

                            //새 키를 캐시에 저장.
                            keyCache.put(key, generateNewMasterKey()); // 새 키를 캐시에 저장
                            log.info("Master Key Changed.");

                            //마스터 키 변경을 메인 서버에 알림.
//                            restTemplate.postForObject(serverUrl,null,Void.class);
                            log.info("Notify Master Key Change to Main Server");
                        }
                    }
                })
                .build();

        //DB확인.
        List<MasterKey> masterKeys = masterKeyRepository.findTop2ByOrderByIdDesc();

        //1) db에없거나 만료된경우
        if(masterKeys == null || masterKeys.isEmpty() || masterKeys.get(0).getExpiredAt().isBefore(LocalDateTime.now())){
            keyCache.put("curMasterKey", generateNewMasterKey());
            System.out.println("마스터 키 새로 생성해서 저장 완료:" + keyCache.getIfPresent("curMasterKey"));
            return;
        }

        //2) db에 있고 만료되지 않은 경우
        //남은 만료시간만큼 설정해서 캐시에 넣기.
        keyCache.put("curMasterKey", new MasterKeyData(masterKeys.get(0).getId(),
                masterKeys.get(0).getValue(),
                masterKeys.get(0).getIv(),
                Duration.between(LocalDateTime.now(), masterKeys.get(0).getExpiredAt()).getSeconds()));
        System.out.println("마스터 키 db에서 가져와서  저장 완료:" + keyCache.getIfPresent("curMasterKey"));
        
        //3)
        //todo db에 위에서 2번째 masterkey있고 만료 안된경우 prev에 넣기
    }

    //캐시에 있는 값 가져오기. 반드시 있어야 하는경우에만 호출됨.
    public MasterKeyData getCurMasterKey(){
        MasterKeyData masterKeyData = keyCache.getIfPresent("curMasterKey");
        if(masterKeyData == null){
            throw new BusinessException(ExceptionCode.NO_MASTERKEY_CACHE_FOUND);
        }
        return masterKeyData;
    }

    public MasterKeyData getPrevMasterKey(){
        return keyCache.getIfPresent("prevMasterKey");
    }

    //새로운 MasterKey값 생성.
    private MasterKeyData generateNewMasterKey() {
        System.out.println("실제 키 생성");
        // 실제 키 생성
        byte[] key = aesUtil.keyGenerator();
        byte[] iv = aesUtil.IVGenerator();
        LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(EXPIRE_TIME);

        // db 저장
        MasterKey masterKey = new MasterKey();
        masterKey.setValue(key);
        masterKey.setIv(iv);
        masterKey.setExpiredAt(expiredAt);
        masterKey = masterKeyRepository.save(masterKey);

        //리턴
        return new MasterKeyData(masterKey.getId(), key, iv, EXPIRE_TIME);
    }

}
