package com.example.universe.simulator.eventservice;

import com.example.universe.simulator.common.config.CommonProperties;
import com.example.universe.simulator.common.config.RabbitMQConfig;
import com.example.universe.simulator.common.config.RabbitMQProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableConfigurationProperties({CommonProperties.class, RabbitMQProperties.class})
@Import(RabbitMQConfig.class)
public class EventServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }
}
