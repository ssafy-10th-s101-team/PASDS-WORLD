package world.pasds.kms.datakey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import world.pasds.kms.datakey.dto.DecryptionKeysRequestDto;
import world.pasds.kms.datakey.dto.DecryptionKeysResponseDto;
import world.pasds.kms.datakey.dto.EncryptionKeysResponseDto;
import world.pasds.kms.datakey.dto.RegenerateKeysResponseDto;
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
    public ResponseEntity<RegenerateKeysResponseDto> regenerateKey(@RequestBody DecryptionKeysRequestDto requestDto){
        RegenerateKeysResponseDto response = dataKeyService.regenerateKey(requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
