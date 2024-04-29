package world.pasds.back.common.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final RedisTemplate redisTemplate;

    public void sendEmail(String toEmail, String subject, String text){
        SimpleMailMessage emailForm = createEmailForm(toEmail, subject, text);
        mailSender.send(emailForm);
    }

    private SimpleMailMessage createEmailForm(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

    public boolean checkExistsValue(String value) {
        return value != null;
    }

    public String getRedisAuthCode(String authCodeRedisKey) {
        return (String) redisTemplate.opsForValue().get(authCodeRedisKey);
    }

    public void setRedisAuthCode(String authCodeRedisKey, String authCode, Duration duration) {
        redisTemplate.opsForValue().set(authCodeRedisKey, authCode, duration);
    }
}
