package com.example.universe.simulator.eventservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Event {

    @Id
    @EqualsAndHashCode.Exclude
    private UUID id;

    private String type;

    private String data;

    private OffsetDateTime time;
}
