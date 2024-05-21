package world.pasds.back.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailAsyncService {
    @Autowired
    @Qualifier("mailSenderList")
    private final List<JavaMailSenderImpl> mailSenderList;

    @Async
    public void sendMessage(String toEmail, String subject, String text) {

        SimpleMailMessage emailForm = createEmailForm(toEmail, subject, text);

        for (int i = 0; i < mailSenderList.size(); i++) {
            try {
                mailSenderList.get(i).send(emailForm);
                System.out.println("비동기 이메일 전송 toEmail = "+toEmail +" 관리자 메일 = "+ mailSenderList.get(i).getUsername()+" 성공");
                return;
            } catch (Exception e) {
            }
        }

        System.out.println("비동기 이메일 전송 toEmail = "+toEmail +" 실패");
    }

    private SimpleMailMessage createEmailForm(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
