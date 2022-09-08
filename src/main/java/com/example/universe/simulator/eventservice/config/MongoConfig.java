package com.example.universe.simulator.eventservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.OffsetDateTime;
import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
            new MongoOffsetDateTimeReader(),
            new MongoOffsetDateTimeWriter()
        ));
    }
}

class MongoOffsetDateTimeReader implements Converter<String, OffsetDateTime> {
    @Override
    public OffsetDateTime convert(final String source) {
        return OffsetDateTime.parse(source);
    }
}

class MongoOffsetDateTimeWriter implements Converter<OffsetDateTime, String> {
    @Override
    public String convert(final OffsetDateTime offsetDateTime) {
        return offsetDateTime.toString();
    }
}
