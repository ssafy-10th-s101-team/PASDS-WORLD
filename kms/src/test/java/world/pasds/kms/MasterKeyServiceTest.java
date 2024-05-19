//package world.pasds.kms;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import world.pasds.kms.masterKey.entity.MasterKey;
//import world.pasds.kms.masterKey.repository.MasterKeyRepository;
//import world.pasds.kms.masterKey.service.MasterKeyService;
//import world.pasds.kms.util.AesUtil;
//
//@SpringBootTest
//public class MasterKeyServiceTest {
//
//    @Mock
//    private MasterKeyRepository masterKeyRepository;
//
//    @Mock
//    private AesUtil aesUtil;
//
//    @InjectMocks
//    private MasterKeyService masterKeyService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        when(aesUtil.keyGenerator()).thenReturn(new byte[32]);
//        when(aesUtil.IVGenerator()).thenReturn(new byte[16]);
//    }
//
//    @Test
//    void testInit() {
//        // init 메서드 테스트
//        masterKeyService.init();
//        assertNotNull(masterKeyService.keyCache.getIfPresent("curMasterKey"));
//    }
//
//    @Test
//    void testGenerateNewMasterKey() {
//        // generateNewMasterKey 메서드 테스트
//        MasterKeyService.MasterKeyData generatedKey = masterKeyService.generateNewMasterKey();
//        assertNotNull(generatedKey);
//        System.out.println(generatedKey);
//        assertNotNull(generatedKey.value);
//        assertNotNull(generatedKey.iv);
//
//        verify(masterKeyRepository, times(1)).save(any(MasterKey.class));
//    }
//}
