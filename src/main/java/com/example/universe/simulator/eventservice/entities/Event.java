package com.example.universe.simulator.eventservice.entities;

import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Event(@Id UUID id, String type, String data, OffsetDateTime time) {}
