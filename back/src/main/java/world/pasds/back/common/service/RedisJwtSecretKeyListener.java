package world.pasds.back.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;

import java.util.concurrent.TimeUnit;

import static org.hibernate.query.sqm.tree.SqmNode.log;

//@Service
public class RedisJwtSecretKeyListener extends KeyExpirationEventMessageListener {
    @Value("${security.jwt.refresh-token-expiration-ms}")
    private int refreshTokenExpirationMs;
    private final RedisTemplate<String, Object> redisTemplate;
    private final KeyService keyService;

    public RedisJwtSecretKeyListener(RedisMessageListenerContainer listenerContainer, RedisTemplate<String, Object> redisTemplate, KeyService keyService) {
        super(listenerContainer);
        this.redisTemplate = redisTemplate;
        this.keyService = keyService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody());

        // "curJwtSecretKey"에 대한 이벤트만 처리
        if (!"curJwtSecretKey".equals(expiredKey)) {
            return; // 이 이벤트를 무시하고 리턴
        }

        // 만료된 키를 prevJwtSecretKey에 설정.
        String expiredSecretKey = (String) redisTemplate.opsForValue().get("backUpJwtSecretKey");

        if (expiredSecretKey == null) {
            log.info("backUpJwtSecretKey NOT FOUND");
            throw new BusinessException(ExceptionCode.KEY_ERROR);
        }

        //사실 prev 만료시간은 refreshtoken에 의존한다. 로직에 따라 의존안할수도, 할수도 있따.
        redisTemplate.opsForValue().set("prevJwtSecretKey", expiredSecretKey, refreshTokenExpirationMs, TimeUnit.SECONDS);

        //새롭게 cur키 발급
        keyService.getJwtSecretKey();
    }

}
