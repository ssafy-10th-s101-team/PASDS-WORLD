package world.pasds.back.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "world.pasds.back.authority.repository",
                "world.pasds.back.dashboard.repository",
                "world.pasds.back.invitaion.repository",
                "world.pasds.back.member.repository",
                "world.pasds.back.notification.repository",
                "world.pasds.back.organization.repository",
                "world.pasds.back.authority.repository",
                "world.pasds.back.privateData.repository.jpa",
                "world.pasds.back.role.repository",
                "world.pasds.back.team.repository",
                "world.pasds.back.totp.repository",
        })
public class JpaRepositoryConfig {
}

