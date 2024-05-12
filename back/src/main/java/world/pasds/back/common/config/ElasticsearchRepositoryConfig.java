package world.pasds.back.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(
        basePackages = "world.pasds.back.privateData.repository.elasticsearch")
public class ElasticsearchRepositoryConfig {
}

