package world.pasds.kms;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import world.pasds.kms.masterKey.entity.MasterKey;
import world.pasds.kms.masterKey.repository.MasterKeyRepository;
import world.pasds.kms.masterKey.service.MasterKeyService;
import world.pasds.kms.util.AesUtil;

import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
public class MasterKeyServiceTest {

    @Mock
    private AesUtil aesUtil;
    @Mock
    private MasterKeyRepository masterKeyRepository;
    @InjectMocks
    private MasterKeyService masterKeyService;

    @BeforeEach
    public void setUp() {
        // 마스터 키 서비스 초기화
        masterKeyService.init();
    }

    @Test
    public void testMasterKeyCachingAndRenewal() throws InterruptedException {
        // 처음 마스터 키 생성 및 반환 테스트
        MasterKeyService.MasterKeyData initialKey = masterKeyService.keyCache.getIfPresent("curMasterKey");
        assertNotNull(initialKey);
        byte[] initialKeyValue = initialKey.value;

        // 키 만료 시간까지 기다리기
        TimeUnit.SECONDS.sleep(60);

        // 만료 후 새 키 확인
        MasterKeyService.MasterKeyData renewedKey = masterKeyService.keyCache.getIfPresent("curMasterKey");
        assertNotNull(renewedKey);
        System.out.println("만료 후 새 키 확인  : " + renewedKey);

    }

    @Test
    public void testMasterKeyGeneration() {
        // 키 생성 시 aesUtil 사용 확인
        when(aesUtil.keyGenerator()).thenReturn(new byte[32]); // 키 생성 시뮬레이션
        when(aesUtil.IVGenerator()).thenReturn(new byte[16]); // IV 생성 시뮬레이션

        masterKeyService.generateNewMasterKey();

        // aesUtil의 메서드가 호출되었는지 확인
        verify(aesUtil, times(1)).keyGenerator();
        verify(aesUtil, times(1)).IVGenerator();

        // 키가 데이터베이스에 저장되었는지 확인
        verify(masterKeyRepository, times(1)).save(any(MasterKey.class));
    }
}
