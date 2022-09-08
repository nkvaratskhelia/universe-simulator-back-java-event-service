package com.example.universe.simulator.eventservice.entities;

import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

public record Event(@Id String id, String type, String data, OffsetDateTime time) {}
