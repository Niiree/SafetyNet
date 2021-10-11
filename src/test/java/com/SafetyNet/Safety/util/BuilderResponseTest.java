package com.SafetyNet.Safety.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class BuilderResponseTest<obj> {

    private BuilderResponse<obj> builderResponseUnderTest;

    @BeforeEach
    void setUp() {
        builderResponseUnderTest = new BuilderResponse<obj>();
    }

    @Test
    @DisplayName("Builder réponse OK")
    void testCustomResponse() {
        // Setup
        String obj = "test";

        // Run the test
        final ResponseEntity<?> result = builderResponseUnderTest.customResponse((obj) obj);

        // Verify the results
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Builder réponse si null")
    void testCustomResponseNull() {
        // Setup
        final obj obj = null;

        // Run the test
        final ResponseEntity<?> result = builderResponseUnderTest.customResponse(null);

        // Verify the results
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    @DisplayName("Builder réponse OK")
    void testResponseBooleanTrue() {
        // Run the test
        final ResponseEntity<?> result = builderResponseUnderTest.responseBoolean(true);

        // Verify the results
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Builder réponse KO")
    void testResponseBoolean() {
        // Run the test
        final ResponseEntity<?> result = builderResponseUnderTest.responseBoolean(false);

        // Verify the results
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
