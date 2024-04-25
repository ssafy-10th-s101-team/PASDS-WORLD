package world.pasds.kms.jwtsecretkey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import world.pasds.kms.util.HmacUtil;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class JwtSecretKeyService {

    private final HmacUtil hmacUtil;

    public String generateJwtSecretKey() {
        return Base64.getEncoder().encodeToString(hmacUtil.keyGenerator());
    }
}
