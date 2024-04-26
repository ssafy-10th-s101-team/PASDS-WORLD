package world.pasds.back.common.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.temporary-token-expiration-ms}")
    private Long temporaryTokenExpirationMs;

    @Value("${security.jwt.access-token-expiration-ms}")
    private Long accessTokenExpirationMs;

    @Value("${security.jwt.refresh-token-expiration-ms}")
    private Long refreshTokenExpirationMs;

    public enum TokenType {
        TEMPORARY, ACCESS, REFRESH
    }

    public String generateJwtToken(Authentication authentication, TokenType tokenType) {
        long expirationMs = 0;
        String tokenTypeClaim = "";

        switch (tokenType) {
            case TEMPORARY:
                expirationMs = temporaryTokenExpirationMs;  // 임시 토큰 만료 시간
                tokenTypeClaim = "temporaryToken";
                break;
            case ACCESS:
                expirationMs = accessTokenExpirationMs;     // 액세스 토큰 만료 시간
                tokenTypeClaim = "accessToken";
                break;
            case REFRESH:
                expirationMs = refreshTokenExpirationMs;    // 리프레시 토큰 만료 시간
                tokenTypeClaim = "refreshToken";
                break;
        }

        String tokenId = UUID.randomUUID().toString();

        // TODO: Redis 저장 로직 추가
        // 예: redisService.storeToken(tokenId, expirationMs);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setId(tokenId)
                .claim("type", tokenTypeClaim)
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateTemporaryJwtToken(Authentication authentication) {
        return generateJwtToken(authentication, TokenType.TEMPORARY);
    }

    public String generateAccessJwtToken(Authentication authentication) {
        return generateJwtToken(authentication, TokenType.ACCESS);
    }

    public String generateRefreshJwtToken(Authentication authentication) {
        return generateJwtToken(authentication, TokenType.REFRESH);
    }

//    public void validateJwtToken(String token) {
//        // 서명, 만료 시간 검증
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token);
//        } catch (ExpiredJwtException e) {
//            throw new BusinessException(ExceptionCode.JWT_EXPIRED);
//        } catch (UnsupportedJwtException e) {
//            throw new BusinessException(ExceptionCode.JWT_UNSUPPORTED);
//        } catch (MalformedJwtException e) {
//            throw new BusinessException(ExceptionCode.JWT_MALFORMED);
//        } catch (SignatureException e) {
//            throw new BusinessException(ExceptionCode.JWT_SIGNATURE);
//        } catch (IllegalArgumentException e) {
//            throw new BusinessException(ExceptionCode.JWT_ARGUMENT);
//        }
//
//        // TODO: ATK 만료니까 RTK 가져와
//
//        // TODO: Redis "memberId_type" 찾아
//
//        // TODO: Redis "memberId_type" 찾아서 TokenId 같은지 확인 해봐
//    }
}
