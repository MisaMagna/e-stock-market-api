package com.cognizant.fse2.estockmarketapi.application.web;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

@Tag("Web")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface WebTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Beginning Web test suite");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Ending Web test suite");
    }
}
