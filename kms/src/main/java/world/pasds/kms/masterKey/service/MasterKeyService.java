package world.pasds.kms.masterKey.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class MasterKeyService {
    byte[] curMasterKey;
    byte[] prevMasterKey;

    @PostConstruct
    public void init(){
       //최초 masterKey 생성

    }

}
