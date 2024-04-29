package world.pasds.kms.datakey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import world.pasds.kms.datakey.dto.*;
import world.pasds.kms.datakey.service.DataKeyService;

@RestController
@RequestMapping("/kms/api")
@RequiredArgsConstructor
public class DataKeyController {

    private final DataKeyService dataKeyService;
    @GetMapping("/generate-key")
    public ResponseEntity<EncryptionKeysResponseDto> generateKey(){
        EncryptionKeysResponseDto response = dataKeyService.generateKey();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/get-key")
    public ResponseEntity<DecryptionKeysResponseDto> getKey(@RequestBody DecryptionKeysRequestDto requestDto){
        DecryptionKeysResponseDto response = dataKeyService.getKey(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/regenerate-key")
    public ResponseEntity<RegenerateKeysResponseDto> reGenerateKey(@RequestBody DecryptionKeysRequestDto requestDto){
        RegenerateKeysResponseDto response = dataKeyService.reGenerateKey(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reencrypt-key")
    public ResponseEntity<ReEncryptionDto> reEncryptKey(@RequestBody ReEncryptionDto dto){
        ReEncryptionDto response = dataKeyService.reEncryptKey(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
