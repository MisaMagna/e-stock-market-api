package com.cognizant.fse2.estockmarketapi.domain;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

@Tag("Domain")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface DomainTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Beginning Domain test suite");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Ending Domain test suite");
    }
}