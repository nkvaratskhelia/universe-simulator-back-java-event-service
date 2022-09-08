package com.example.universe.simulator.eventservice.integration;

import com.example.universe.simulator.common.test.AbstractSpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.RabbitMQContainer;

@AbstractSpringBootTest
@AutoConfigureWebTestClient
abstract class AbstractIntegrationTest {

    private static final RabbitMQContainer RABBITMQ_CONTAINER;
    private static final MongoDBContainer MONGODB_CONTAINER;

    @Autowired
    protected WebTestClient webClient;

    static {
        RABBITMQ_CONTAINER = new RabbitMQContainer("rabbitmq:3.10.7-management");
        MONGODB_CONTAINER = new MongoDBContainer("mongo:5.0.11");

        RABBITMQ_CONTAINER.start();
        MONGODB_CONTAINER.start();
    }

    @DynamicPropertySource
    private static void addTestContainersProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", RABBITMQ_CONTAINER::getHost);
        registry.add("spring.rabbitmq.port", RABBITMQ_CONTAINER::getAmqpPort);
        registry.add("spring.rabbitmq.username", RABBITMQ_CONTAINER::getAdminUsername);
        registry.add("spring.rabbitmq.password", RABBITMQ_CONTAINER::getAdminPassword);

        registry.add("spring.data.mongodb.uri", MONGODB_CONTAINER::getReplicaSetUrl);
    }
}
