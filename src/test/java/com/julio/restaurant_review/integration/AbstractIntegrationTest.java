package com.julio.restaurant_review.integration;

import com.redis.testcontainers.RedisContainer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;


@ContextConfiguration(initializers= AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.3-alpine"));
        static RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:7.4.2-alpine"));

        private static void startContainers() {
            Startables.deepStart(postgres, redis).join();
        }

        private static Map<String, String> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", postgres.getJdbcUrl(),
                    "spring.datasource.username", postgres.getUsername(),
                    "spring.datasource.password", postgres.getPassword(),
                    "data.redis.host", redis.getHost(),
                    "data.redis.port", String.valueOf(redis.getRedisPort())
                    );
        }

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testContainers = new MapPropertySource("testContainers", (Map) createConnectionConfiguration());
            environment.getPropertySources().addFirst(testContainers);
        }
    }
}
