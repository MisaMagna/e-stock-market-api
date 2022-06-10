package com.cognizant.fse2.estockmarketapi.infrastructure.persistance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

@Tag("Persistence")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface PersistenceTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Beginning Persistence test suite");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Ending Persistence test suite");
    }
}

