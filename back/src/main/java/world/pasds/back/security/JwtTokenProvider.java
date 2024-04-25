package world.pasds.back.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.access-token-expiration-ms}")
    private Long accessTokenExpirationMs;

    @Value("${security.jwt.refresh-token-expiration-ms}")
    private Long refreshTokenExpirationMs;

    public JwtTokenDto generateJwtTokenDto(Authentication authentication) {

        long now = (new Date()).getTime();

        // UUID 생성
        String accessTokenId = UUID.randomUUID().toString();
        String refreshTokenId = UUID.randomUUID().toString();

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setId(accessTokenId)
                .claim("type", "accessToken")
                .setExpiration(new Date(now + accessTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setId(refreshTokenId)
                .claim("type", "refreshToken")
                .setExpiration(new Date(now + refreshTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();

        // TODO: Redis에 저장하기!

        return JwtTokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 토큰 정보를 검증하는 메서드
    public void validateJwtToken(String token) {
        // Sign과 만료 시간 검증
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ExceptionCode.JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new BusinessException(ExceptionCode.JWT_UNSUPPORTED);
        } catch (MalformedJwtException e) {
            throw new BusinessException(ExceptionCode.JWT_MALFORMED);
        } catch (SignatureException e) {
            throw new BusinessException(ExceptionCode.JWT_SIGNATURE);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ExceptionCode.JWT_ARGUMENT);
        }

        // TODO: ATK 만료니까 RTK 가져와

        // TODO: Redis "memberId_type" 찾아

        // TODO: Redis "memberId_type" 찾아서 TokenId 같은지 확인 해봐
    }

//    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
//    public Authentication getAuthentication(String accessToken) {
//        // Jwt 토큰 복호화
//        Claims claims = parseClaims(accessToken);
//
//        if (claims.get("auth") == null) {
//            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
//        }
//
//        // 클레임에서 권한 정보 가져오기
//        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        // UserDetails 객체를 만들어서 Authentication return
//        // UserDetails: interface, User: UserDetails를 구현한 class
//        UserDetails principal = new User(claims.getSubject(), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
//    }


}
