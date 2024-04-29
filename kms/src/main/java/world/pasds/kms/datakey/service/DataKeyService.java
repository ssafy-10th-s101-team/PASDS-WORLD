package world.pasds.kms.datakey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.kms.datakey.dto.*;
import world.pasds.kms.datakey.model.MasterKeyData;
import world.pasds.kms.masterkey.service.MasterKeyService;
import world.pasds.kms.util.AesUtil;

import java.util.Base64;

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
                .build();

        return dto;
    }

    public DecryptionKeysResponseDto getKey(DecryptionKeysRequestDto requestDto) {
        MasterKeyData masterKeyData = masterKeyService.getCurMasterKey();
        byte[] masterKey = masterKeyData.getValue();
        byte[] masterIv = masterKeyData.getIv();

        byte[] dataKey = aesUtil.decrypt(Base64.getDecoder().decode(requestDto.getEncryptedDataKey()),masterKey,masterIv);
        byte[] iv = aesUtil.decrypt(Base64.getDecoder().decode(requestDto.getEncryptedIv()),masterKey,masterIv);

        DecryptionKeysResponseDto dto = DecryptionKeysResponseDto.builder()
                .dataKey(Base64.getEncoder().encodeToString(dataKey))
                .iv(Base64.getEncoder().encodeToString(iv))
                .build();

        return dto;
    }

    public RegenerateKeysResponseDto reGenerateKey(DecryptionKeysRequestDto requestDto) {
        //TODO
        return null;
    }

    @Transactional
    public ReEncryptionDto reEncryptKey(ReEncryptionDto requestDto){
        //MasterKey 가져오기
        MasterKeyData prevMasterKey = masterKeyService.getPrevMasterKey();
        MasterKeyData curMasterKey = masterKeyService.getCurMasterKey();

        //prev로 원본 데이터 키 받기
        byte[] dataKey = aesUtil.decrypt(Base64.getDecoder().decode(requestDto.getEncryptedDataKey()),prevMasterKey.getValue(),prevMasterKey.getIv());
        byte[] iv = aesUtil.decrypt(Base64.getDecoder().decode(requestDto.getEncryptedIv()),prevMasterKey.getValue(),prevMasterKey.getIv());

        //cur로 다시 암호화하기
        byte[] encryptedDataKey = aesUtil.encrypt(dataKey, curMasterKey.getValue(),curMasterKey.getIv());
        byte[] encryptedIv = aesUtil.encrypt(iv, curMasterKey.getValue(),curMasterKey.getIv());

        //reponseDto에 담아서 넣기
        ReEncryptionDto responseDto = ReEncryptionDto.builder()
                        .encryptedDataKey(Base64.getEncoder().encodeToString(encryptedDataKey))
                        .encryptedIv(Base64.getEncoder().encodeToString(encryptedIv))
                        .build();

        return responseDto;
    }
}
