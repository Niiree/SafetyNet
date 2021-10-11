package com.SafetyNet.Safety.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonTest {

    private Person personUnderTest;

    @BeforeEach
    void setUp() {

        personUnderTest = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
    }

    @Test
    @DisplayName("Model Person IsAdult")
    void testIsAdult() {
        // Setup
        personUnderTest.setBirthdate("24/09/2000");
        // Run the test
        final boolean result = personUnderTest.isAdult();

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Model Person IsAdult true")
    void testIsAdultNo() {
        // Setup
        personUnderTest.setBirthdate("24/09/2021");
        // Run the test
        final boolean result = personUnderTest.isAdult();

        // Verify the results
        assertThat(result).isFalse();
    }
}
