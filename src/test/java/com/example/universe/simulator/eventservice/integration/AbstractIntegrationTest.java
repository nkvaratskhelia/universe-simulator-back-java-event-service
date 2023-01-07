package com.example.universe.simulator.eventservice.integration;

import com.example.universe.simulator.common.test.AbstractSpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;

@AbstractSpringBootTest
@AutoConfigureWebTestClient
abstract class AbstractIntegrationTest {

    private static final RabbitMQContainer RABBITMQ_CONTAINER;
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

    @Autowired
    protected WebTestClient webClient;

    static {
        RABBITMQ_CONTAINER = new RabbitMQContainer("rabbitmq:3.11.6-management");
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.1");

        RABBITMQ_CONTAINER.start();
        POSTGRESQL_CONTAINER.start();
    }

    @DynamicPropertySource
    private static void addTestContainersProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", RABBITMQ_CONTAINER::getHost);
        registry.add("spring.rabbitmq.port", RABBITMQ_CONTAINER::getAmqpPort);
        registry.add("spring.rabbitmq.username", RABBITMQ_CONTAINER::getAdminUsername);
        registry.add("spring.rabbitmq.password", RABBITMQ_CONTAINER::getAdminPassword);

        registry.add("spring.r2dbc.url", () -> POSTGRESQL_CONTAINER.getJdbcUrl().replace("jdbc", "r2dbc"));
        registry.add("spring.r2dbc.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.r2dbc.password", POSTGRESQL_CONTAINER::getPassword);

        // needed because liquibase doesn't support r2dbc yet
        registry.add("spring.liquibase.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.liquibase.user", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.liquibase.password", POSTGRESQL_CONTAINER::getPassword);
    }
}
