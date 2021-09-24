package com.SafetyNet.Safety.util;

import org.junit.jupiter.api.BeforeEach;
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
    void testCustomResponse() {
        // Setup
         String obj = "test";

        // Run the test
        final ResponseEntity<?> result = builderResponseUnderTest.CustomResponse((obj) obj);

        // Verify the results
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testCustomResponseNull() {
        // Setup
        final obj obj = null;

        // Run the test
        final ResponseEntity<?> result = builderResponseUnderTest.CustomResponse(null);

        // Verify the results
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    void testResponseBooleanTrue() {
        // Setup

        // Run the test
        final ResponseEntity<?> result = builderResponseUnderTest.ResponseBoolean(true);

        // Verify the results
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void testResponseBoolean() {
        // Setup

        // Run the test
        final ResponseEntity<?> result = builderResponseUnderTest.ResponseBoolean(false);

        // Verify the results
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}
