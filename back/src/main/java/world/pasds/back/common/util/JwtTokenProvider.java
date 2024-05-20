package world.pasds.back.common.util;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.common.service.KeyService;
import world.pasds.back.member.entity.CustomUserDetails;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final RedisTemplate<String, String> redisTemplate;

    private final KeyService keyService;

    @Value("${security.jwt.temporary-token-expiration-ms}")
    private int temporaryTokenExpirationMs;

    @Value("${security.jwt.access-token-expiration-ms}")
    private int accessTokenExpirationMs;

    @Value("${security.jwt.refresh-token-expiration-ms}")
    private int refreshTokenExpirationMs;

    @Value("${security.jwt.email-token-expiration-ms}")
    private int emailTokenExpirationMs;

    public enum TokenType {
        TEMPORARY, ACCESS, REFRESH, EMAIL
    }

    public String generateToken(Long memberId, TokenType tokenType, boolean isNew) {

        String tokenId = UUID.randomUUID().toString();
        int expirationMs = 0;
        switch (tokenType) {
            case TEMPORARY:
                expirationMs = temporaryTokenExpirationMs;  // 임시 토큰 만료 시간
                break;
            case ACCESS:
                expirationMs = accessTokenExpirationMs;     // 액세스 토큰 만료 시간
                break;
            case REFRESH:
                expirationMs = refreshTokenExpirationMs;    // 리프레시 토큰 만료 시간
                break;
        }

        String redisKey = memberId + "_" + tokenType.name();

        if (isNew) {
            redisTemplate.opsForValue().set(redisKey, tokenId, expirationMs / 1000, TimeUnit.SECONDS); // Redis에 저장하면서 TTL 설정
        } else {
            redisTemplate.opsForValue().set(redisKey, tokenId);
        }

        String curJwtSecretKey = keyService.getJwtSecretKey();
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, curJwtSecretKey)
                .setSubject(memberId.toString())
                .setId(tokenId)
                .claim("tokenType", tokenType.name())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .compact();
    }

    public String generateEmailToken(String email) {
        TokenType tokenType = TokenType.EMAIL;
        String tokenId = UUID.randomUUID().toString();
        int expirationMs = emailTokenExpirationMs;
        String redisKey = email + "_" + tokenType.name();
        redisTemplate.opsForValue().set(redisKey, tokenId, expirationMs / 1000, TimeUnit.SECONDS); // Redis에 저장하면서 TTL 설정
        String curJwtSecretKey = keyService.getJwtSecretKey();
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, curJwtSecretKey)
                .setSubject(email)
                .setId(tokenId)
                .claim("tokenType", tokenType.name())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .compact();
    }

    public Authentication getAuthentication(String token, String jwtSecretKey) {

        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
        }

        // 서명 불퉁과
        catch (SignatureException | MalformedJwtException | UnsupportedJwtException ex) {
            throw new BusinessException(ExceptionCode.INVALID_SIGNATURE);
        }

        // 기간 불통과
        catch (ExpiredJwtException ex) {
            throw new BusinessException(ExceptionCode.TOKEN_EXPIRED);
        }

        Long memberId = Long.parseLong(claims.getSubject());
        String tokenType = claims.get("tokenType", String.class);

        String redisKey = memberId + "_" + tokenType;
        String storedToken = redisTemplate.opsForValue().get(redisKey);

        // 레디스에 해당 유저는 토큰이 없슴
        if (storedToken == null) {
            throw new BusinessException(ExceptionCode.TOKEN_NOT_FOUND);
        }

        // 레디스에 해당 유저의 최신 토큰과 다름
        if (!storedToken.equals(claims.getId())) {
            throw new BusinessException(ExceptionCode.TOKEN_MISMATCH);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(memberId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities()
        );
        return authentication;

    }

    public Authentication getAuthenticationByEmailToken(String token, String jwtSecretKey) {

        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
        }

        // 서명 불퉁과
        catch (SignatureException | MalformedJwtException | UnsupportedJwtException ex) {
            throw new BusinessException(ExceptionCode.INVALID_SIGNATURE);
        }

        // 기간 불통과
        catch (ExpiredJwtException ex) {
            throw new BusinessException(ExceptionCode.TOKEN_EXPIRED);
        }

        String email = claims.getSubject();
        String tokenType = claims.get("tokenType", String.class);

        String redisKey = email + "_" + tokenType;
        String storedToken = redisTemplate.opsForValue().get(redisKey);

        // 레디스에 해당 유저는 토큰이 없슴
        if (storedToken == null) {
            throw new BusinessException(ExceptionCode.TOKEN_NOT_FOUND);
        }

        // 레디스에 해당 유저의 최신 토큰과 다름
        if (!storedToken.equals(claims.getId())) {
            throw new BusinessException(ExceptionCode.TOKEN_MISMATCH);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities()
        );
        return authentication;

    }
}
