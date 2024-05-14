package world.pasds.kms.datakey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.kms.common.exception.BusinessException;
import world.pasds.kms.common.exception.ExceptionCode;
import world.pasds.kms.datakey.dto.*;
import world.pasds.kms.datakey.model.MasterKeyData;
import world.pasds.kms.masterkey.service.MasterKeyService;
import world.pasds.kms.util.AesUtil;

import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DataKeyService {
    private final AesUtil aesUtil;
    private final MasterKeyService masterKeyService;
    public EncryptionKeysResponseDto generateKey() {
        byte[] dataKey = aesUtil.keyGenerator();
        byte[] iv = aesUtil.IVGenerator();

        MasterKeyData masterKeyData = masterKeyService.getCurMasterKey();
        byte[] masterKey = masterKeyData.getValue();
        byte[] masterIv = masterKeyData.getIv();

        byte[] encryptedDataKey = aesUtil.encrypt(dataKey,masterKey,masterIv);
        byte[] encryptedIv = aesUtil.encrypt(iv,masterKey,masterIv);

        EncryptionKeysResponseDto dto = EncryptionKeysResponseDto.builder()
                .dataKey(Base64.getEncoder().encodeToString(dataKey))
                .iv(Base64.getEncoder().encodeToString(iv))
                .encryptedDataKey(Base64.getEncoder().encodeToString(encryptedDataKey))
                .encryptedIv(Base64.getEncoder().encodeToString(encryptedIv))
                .masterKeyVersion(masterKeyData.getId())
                .build();

        return dto;
    }

    public DecryptionKeysResponseDto getKey(EncryptedDataKeyDto requestDto) {
        MasterKeyData curMasterKeyData = masterKeyService.getCurMasterKey();
        MasterKeyData prevMasterKeyData = masterKeyService.getPrevMasterKey();
        byte[] masterKey;
        byte[] masterIv;

        //들고 온 masterKey 버전이 cur 버전인 경우
        if(requestDto.getMasterKeyVersion().equals(curMasterKeyData.getId())) {
            masterKey = curMasterKeyData.getValue();
            masterIv = curMasterKeyData.getIv();
        }
        //들고 온 masterKey 버전이 prev 버전인 경우
        else if(requestDto.getMasterKeyVersion().equals(prevMasterKeyData.getId())) {
            masterKey = prevMasterKeyData.getValue();
            masterIv = prevMasterKeyData.getIv();
        }
        else{
            throw new BusinessException(ExceptionCode.KEY_EXPIRED);
        }

        byte[] dataKey = aesUtil.decrypt(Base64.getDecoder().decode(requestDto.getEncryptedDataKey()),masterKey,masterIv);
        byte[] iv = aesUtil.decrypt(Base64.getDecoder().decode(requestDto.getEncryptedIv()),masterKey,masterIv);
        return DecryptionKeysResponseDto.builder()
                .dataKey(Base64.getEncoder().encodeToString(dataKey))
                .iv(Base64.getEncoder().encodeToString(iv))
                .build();
    }

    public RegenerateKeysResponseDto reGenerateKey(EncryptedDataKeyDto requestDto) {
        EncryptionKeysResponseDto allNewDataKey = generateKey();
        DecryptionKeysResponseDto plainOldDataKey = getKey(requestDto);

        return RegenerateKeysResponseDto.builder()
                .oldDataKey(plainOldDataKey.getDataKey())
                .oldIv(plainOldDataKey.getIv())
                .newDataKey(allNewDataKey.getDataKey())
                .newIv(allNewDataKey.getIv())
                .encryptedNewDataKey(allNewDataKey.getEncryptedDataKey())
                .encryptedNewIv(allNewDataKey.getEncryptedIv())
                .masterKeyVersion(allNewDataKey.getMasterKeyVersion())
                .build();
    }

    @Transactional
    public EncryptedDataKeyDto reEncryptKey(EncryptedDataKeyDto requestDto){

        //복호화
        DecryptionKeysResponseDto plainOldDataKey = getKey(requestDto);

        //MasterKey 가져오기
        MasterKeyData curMasterKey = masterKeyService.getCurMasterKey();

        //cur로 다시 암호화하기
        byte[] encryptedDataKey = aesUtil.encrypt(Base64.getDecoder().decode(plainOldDataKey.getDataKey()), curMasterKey.getValue(),curMasterKey.getIv());
        byte[] encryptedIv = aesUtil.encrypt(Base64.getDecoder().decode(plainOldDataKey.getIv()), curMasterKey.getValue(),curMasterKey.getIv());

        //reponseDto에 담아서 리턴
        return EncryptedDataKeyDto.builder()
                .encryptedDataKey(Base64.getEncoder().encodeToString(encryptedDataKey))
                .encryptedIv(Base64.getEncoder().encodeToString(encryptedIv))
                .masterKeyVersion(curMasterKey.getId())
                .build();
    }
}
