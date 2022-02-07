package com.example.universe.simulator.eventservice.common.abstractions;

import com.example.universe.simulator.common.test.AbstractTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@AbstractTest
public abstract class AbstractWebFluxTest {

    @Autowired
    protected WebTestClient webClient;
}
