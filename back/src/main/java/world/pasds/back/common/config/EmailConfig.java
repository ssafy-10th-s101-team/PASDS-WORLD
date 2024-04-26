package world.pasds.back.common.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ConfigurationProperties(prefix="spring.mail")
public class EmailConfig {
	@Value("${host}")
	private String host;
	@Value("${port}")
	private int port;
	@Value("${username}")
	private String username;
	@Value("${password}")
	private String password;
	@Value("${properties.mail.smtp.auth}")
	private boolean auth;
	@Value("${properties.mail.smtp.starttls.enable}")
	private boolean starttlsEnable;
	@Value("${properties.mail.smtp.starttls.required}")
	private boolean starttlsRequired;
	@Value("${properties.mail.smtp.connectiontimeout}")
	private int connectionTimeout;
	@Value("${properties.mail.smtp.timeout}")
	private int timeout;
	@Value("${properties.mail.smtp.writetimeout}")
	private int writeTimeout;


	@Bean
	public JavaMailSender totpMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setJavaMailProperties(getMailProperties());
		return mailSender;
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
