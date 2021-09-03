package com.SafetyNet.Safety.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonTest {

    private Person personUnderTest;

    @BeforeEach
    void setUp() {
        personUnderTest = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
    }

    @Test
    void testIsAdult() {
        // Setup

        // Run the test
        final boolean result = personUnderTest.isAdult();

        // Verify the results
        assertThat(result).isTrue();
    }
}
