package world.pasds.back.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnable;
    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private boolean starttlsRequired;
    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private int connectionTimeout;
    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private int timeout;
    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
    private int writeTimeout;

    private final MailProperties mailProperties;

    public EmailConfig(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    @Configuration
    @ConfigurationProperties(prefix = "spring.mail")
    @Getter
    @Setter
    public static class MailProperties {
        private List<EmailAccount> accounts;

        @Getter
        @Setter
        public static class EmailAccount {
            private String username;
            private String password;

        }
    }

    @Bean
    @Qualifier("mailSenderList")
    public List<JavaMailSenderImpl> mailSenderList() {
        List<JavaMailSenderImpl> mailSenderList = new ArrayList<>();
        for (MailProperties.EmailAccount account : mailProperties.getAccounts()) {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(account.getUsername());
            mailSender.setPassword(account.getPassword());
            mailSender.setDefaultEncoding("UTF-8");
            mailSender.setJavaMailProperties(getMailProperties());
            mailSenderList.add(mailSender);
        }
        return mailSenderList;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttlsEnable);
        properties.put("mail.smtp.starttls.required", starttlsRequired);
        properties.put("mail.smtp.connectiontimeout", connectionTimeout);
        properties.put("mail.smtp.timeout", timeout);
        properties.put("mail.smtp.writetimeout", writeTimeout);
        return properties;
    }

}
