package world.pasds.back.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import world.pasds.back.member.entity.CustomUserDetails;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.temporary-token-expiration-ms}")
    private int temporaryTokenExpirationMs;

    @Value("${security.jwt.access-token-expiration-ms}")
    private int accessTokenExpirationMs;

    @Value("${security.jwt.refresh-token-expiration-ms}")
    private int refreshTokenExpirationMs;

    public enum TokenType {
        TEMPORARY, ACCESS, REFRESH
    }

    public String generateToken(Long memberId, TokenType tokenType) {

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

        // TODO: Redis 저장 로직 추가
        // TODO: memberId.toString() _ tokenType.name() : tokenId , 만료 시간: expirationMs
        // TODO: 12345_TEMPORARY : 42be7a28-f96d-46b2-b111-2ad44d309525 , 만료 시간: expirationMs

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setSubject(memberId.toString())
                .setId(tokenId)
                .claim("tokenType", tokenType.name())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .compact();
    }

    public String generateTemporaryToken(Long memberId) {
        return generateToken(memberId, TokenType.TEMPORARY);
    }

    public String generateAccessToken(Long memberId) {
        return generateToken(memberId, TokenType.ACCESS);
    }

    public String generateRefreshToken(Long memberId) {
        return generateToken(memberId, TokenType.REFRESH);
    }

    public Authentication getAuthentication(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            Long memberId = Long.parseLong(claims.getSubject());

            // TODO: Redis 검사 로직 추가

            CustomUserDetails customUserDetails = new CustomUserDetails(memberId);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities()
            );

            return authentication;

        } catch (Exception e) {
            return null;
        }
    }
}
