package com.example.universe.simulator.eventservice.repositories;

import com.example.universe.simulator.eventservice.entities.Event;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import java.util.UUID;

public interface EventRepository extends ReactiveSortingRepository<Event, UUID> {}
