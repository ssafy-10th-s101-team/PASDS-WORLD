package world.pasds.kms.jwtsecretkey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.pasds.kms.jwtsecretkey.service.JwtSecretKeyService;

@RestController
@RequestMapping("/kms/api/jwt-secret-key")
@RequiredArgsConstructor
public class JwtSecretKeyController {
    private final JwtSecretKeyService jwtSecretKeyService;
    @GetMapping("/generate-key")
    public ResponseEntity<String> generateJwtSecretKey(){
        return new ResponseEntity<>(jwtSecretKeyService.generateJwtSecretKey(), HttpStatus.OK);
    }

}
