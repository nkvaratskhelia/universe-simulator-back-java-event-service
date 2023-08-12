package com.example.universe.simulator.eventservice.integration;

import com.example.universe.simulator.common.test.AbstractSpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.lifecycle.Startables;

@AbstractSpringBootTest
@AutoConfigureWebTestClient
abstract class AbstractIntegrationTest {

    @ServiceConnection
    private static final RabbitMQContainer RABBITMQ_CONTAINER;

    @ServiceConnection
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

    @Autowired
    protected WebTestClient webClient;

    static {
        RABBITMQ_CONTAINER = new RabbitMQContainer("rabbitmq:3.12.2-management");
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.4");

        Startables.deepStart(RABBITMQ_CONTAINER, POSTGRESQL_CONTAINER).join();
    }
}
