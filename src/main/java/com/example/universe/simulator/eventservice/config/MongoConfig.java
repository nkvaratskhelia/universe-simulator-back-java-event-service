package com.example.universe.simulator.eventservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Configuration
public class MongoConfig {

    public static final String DATE_FIELD = "time";
    public static final String OFFSET_FIELD = "offset";

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
            new MongoOffsetDateTimeReader(),
            new MongoOffsetDateTimeWriter()
        ));
    }
}

@ReadingConverter
class MongoOffsetDateTimeReader implements Converter<String, OffsetDateTime> {
    @Override
    public OffsetDateTime convert(final String source) {
        return OffsetDateTime.parse(source).withOffsetSameInstant(ZoneOffset.UTC);
    }
}

@WritingConverter
@Slf4j
class MongoOffsetDateTimeWriter implements Converter<OffsetDateTime, String> {
    @Override
    public String convert(final OffsetDateTime offsetDateTime) {
        return offsetDateTime.toString();
    }
}
