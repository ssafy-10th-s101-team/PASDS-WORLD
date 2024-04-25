package world.pasds.kms.datakey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import world.pasds.kms.datakey.dto.DecryptionKeysRequestDto;
import world.pasds.kms.datakey.dto.DecryptionKeysResponseDto;
import world.pasds.kms.datakey.dto.EncryptionKeysResponseDto;
import world.pasds.kms.datakey.dto.RegenerateKeysResponseDto;
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

        EncryptionKeysResponseDto dto = new EncryptionKeysResponseDto();
        dto.setDataKey(Base64.getEncoder().encodeToString(dataKey));
        dto.setIv(Base64.getEncoder().encodeToString(iv));
        dto.setEncryptedDataKey(Base64.getEncoder().encodeToString(encryptedDataKey));
        dto.setEncryptedIv(Base64.getEncoder().encodeToString(encryptedIv));

        return dto;
    }

    public DecryptionKeysResponseDto getKey(DecryptionKeysRequestDto requestDto) {
        MasterKeyData masterKeyData = masterKeyService.getCurMasterKey();
        byte[] masterKey = masterKeyData.getValue();
        byte[] masterIv = masterKeyData.getIv();

        byte[] dataKey = aesUtil.decrypt(Base64.getDecoder().decode(requestDto.getEncryptedDataKey()),masterKey,masterIv);
        byte[] iv = aesUtil.decrypt(Base64.getDecoder().decode(requestDto.getEncryptedIv()),masterKey,masterIv);

        DecryptionKeysResponseDto dto = new DecryptionKeysResponseDto();

        dto.setDataKey(Base64.getEncoder().encodeToString(dataKey));
        dto.setIv(Base64.getEncoder().encodeToString(iv));
        return dto;
    }

    public RegenerateKeysResponseDto regenerateKey(DecryptionKeysRequestDto requestDto) {
        //TODO
        return null;
    }
}
