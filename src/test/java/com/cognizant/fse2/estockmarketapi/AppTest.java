package com.cognizant.fse2.estockmarketapi;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

@Tag("App")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface AppTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Beginning Full App test suite");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Ending Full App test suite");
    }
}