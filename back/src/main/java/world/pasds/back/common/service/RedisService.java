package world.pasds.back.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String,String> redisTemplate;

//    wlsdyd1128@naver.com_EMAIL
//    AuthCode wlsdyd4@naver.com

    public void deleteEmailJti(String email){
        String redisKey = email+"_EMAIL";
        redisTemplate.delete(redisKey);
    }

    public String getEmailJti(String email){
        String redisKey = email+"_EMAIL";
        return redisTemplate.opsForValue().get(redisKey);
    }

    public String getAuthCode(String email){
        String redisKey = "AuthCode "+email;
        return redisTemplate.opsForValue().get(redisKey);
    }

    public Long getAccessTokenExpirationTimeLeft(Long memberId){
        String redisKey = memberId+"_ACCESS";
        return redisTemplate.getExpire(redisKey);
    }

    public Long getRefreshTokenExpirationTimeLeft(Long memberId){
        String redisKey = memberId+"_REFRESH";
        return redisTemplate.getExpire(redisKey);
    }
}
