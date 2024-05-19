package world.pasds.back.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import reactor.core.publisher.Sinks;
import world.pasds.back.common.util.AesUtil;
import world.pasds.back.notification.entity.dto.response.NotificationResponseDto;

@Configuration
public class AppConfig {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public AesUtil aesUtil() {
		return new AesUtil();
	}

	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
		return new JPAQueryFactory(entityManager);
	}

	@Bean
	public Sinks.Many<NotificationResponseDto> notificationSink() {
		return Sinks.many().multicast().onBackpressureBuffer();
	}
}
