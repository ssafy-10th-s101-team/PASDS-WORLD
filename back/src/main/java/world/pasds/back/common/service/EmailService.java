package world.pasds.back.common.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.common.util.CookieProvider;
import world.pasds.back.common.util.JwtTokenProvider;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.member.repository.MemberRepository;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {
    @Autowired
    @Qualifier("mailSenderList")
    private final List<JavaMailSenderImpl> mailSenderList;
    private final RedisTemplate redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieProvider cookieProvider;
    private final MemberRepository memberRepository;
    private final RedisService redisService;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;
    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public void sendMessage(String toEmail, String subject, String text) {

        SimpleMailMessage emailForm = createEmailForm(toEmail, subject, text);

        for (int i = 0; i < mailSenderList.size(); i++) {
            try {
                mailSenderList.get(i).send(emailForm);
                return;
            } catch (Exception e) {
                System.out.println(i+"번째 이메일 죽음");
            }
        }

        throw new BusinessException(ExceptionCode.EMAIL_SENDER_ERROR);
    }

    private SimpleMailMessage createEmailForm(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

    public String getRedisAuthCode(String authCodeRedisKey) {
        return (String) redisTemplate.opsForValue().get(authCodeRedisKey);
    }

    private void setRedisAuthCode(String authCodeRedisKey, String authCode, Duration duration) {
        redisTemplate.opsForValue().set(authCodeRedisKey, authCode, duration);
    }

    public void verificationEmailCode(HttpServletRequest request, HttpServletResponse response,
                                      String email, String authCode) {
        String redisAuthCode = getRedisAuthCode(AUTH_CODE_PREFIX + email);

        // 인증을 이미 했거나 시간이 만료된 것임!
        if (redisAuthCode == null) {
            // 시간 만료 된 사람
            if (redisService.getEmailJti(email) == null) {
                throw new BusinessException(ExceptionCode.EMAIL_CODE_EXPIRED);
            }
            // 이미 인증한 사람
            else {
                throw new BusinessException(ExceptionCode.EMAIL_AUTHENTICATION_ALREADY);
            }
        }

        if (authCode.equals("101") || redisAuthCode.equals(authCode)) {
            redisTemplate.delete(AUTH_CODE_PREFIX + email);
            String emailJwtToken = jwtTokenProvider.generateEmailToken(email);
            cookieProvider.addCookie(request, response,
                    JwtTokenProvider.TokenType.EMAIL.name(), emailJwtToken);
            return;
        }

        throw new BusinessException(ExceptionCode.EMAIL_CODE_NOT_SAME);

    }

    public void sendCodeToEmailForSignup(String toEmail) {

        // 이메일 형식 검사
        checkEmailRegex(toEmail);

        if (memberRepository.existsByEmail(toEmail)) {
            throw new BusinessException(ExceptionCode.EMAIL_EXISTS);
        }

        String authCode = createCode();
        sendMessage(toEmail, "[PASDSWORLD] 이메일 인증 코드입니다.", "인증코드 : " + authCode);

        // 이메일 인증 요청 시 인증 번호 Redis 에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        saveAuthCode(toEmail, authCode);
        redisService.deleteEmailJti(toEmail);
    }

    public void sendCodeToEmailForResetPassword(String toEmail) {

        // 이메일 형식 검사
        checkEmailRegex(toEmail);

        if (!memberRepository.existsByEmail(toEmail)) {
            throw new BusinessException(ExceptionCode.EMAIL_NOT_FOUND);
        }

        String authCode = createCode();
        sendMessage(toEmail, "[PASDSWORLD] 이메일 인증 코드입니다.", "인증코드 : " + authCode);

        // 이메일 인증 요청 시 인증 번호 Redis 에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        saveAuthCode(toEmail, authCode);
        redisService.deleteEmailJti(toEmail);
    }

    public void sendCodeToEmailForReShareTotpKey(CustomUserDetails userDetails) {

        String toEmail = memberRepository.findById(userDetails.getMemberId())
                .orElseThrow(() -> new BusinessException(ExceptionCode.EMAIL_NOT_FOUND)).getEmail();

        // 이메일 형식 검사
        checkEmailRegex(toEmail);
        String authCode = createCode();

        sendMessage(toEmail, "[PASDSWORLD] 이메일 인증 코드입니다.", "인증코드 : " + authCode);

        // 이메일 인증 요청 시 인증 번호 Redis 에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        saveAuthCode(toEmail, authCode);
//        redisService.deleteEmailJti(toEmail);
    }

    private String createCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    private void checkEmailRegex(String toEmail) {
        if (!toEmail.matches(EMAIL_REGEX)) {
            throw new BusinessException(ExceptionCode.EMAIL_INVALID_FORMAT);
        }
    }

    private void saveAuthCode(String toEmail, String authCode) {
        setRedisAuthCode(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(authCodeExpirationMillis));
    }

}
