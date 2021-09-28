package com.SafetyNet.Safety.util;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FiltreTest {

    private Filtre filtreUnderTest;

    @BeforeEach
    void setUp() {
        filtreUnderTest = new Filtre();
    }

    @Test
    void testFiltreAllExceptListPerson() {
        // Setup
        final List<Person> personList = Arrays.asList(new Person("firstName", "lastName", "address", "city", "zip", "phone", "email"));
        final String expectedResult = "{\"value\":[{\"city\":\"city\"}],\"serializationView\":null,\"filters\":{\"defaultFilter\":null}}";
        JsonObject expected = new JsonObject();
        expected.addProperty("value",Arrays.asList("c:city").toString());
        expected.addProperty("serializationView","city");
        expected.addProperty("filters","city");

        // Run the test
        final JsonObject result = filtreUnderTest.filtreAllExceptListPerson(personList, "city");

        // Verify the results

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testFiltreAllExceptListFirestation() {
        // Setup
        final List<FireStation> fireStationList = Arrays.asList(new FireStation(Arrays.asList("value"), 0));
        final JsonObject expectedResult = new JsonObject();

        // Run the test
        final JsonObject result = filtreUnderTest.filtreAllExceptListFirestation(fireStationList, "ListAllExcept");

        // Verify the results

    }

    @Test
    void testFiltreAllExceptFirestation() {
        // Setup
        final FireStation fireStationList = new FireStation(Arrays.asList("value"), 0);
        final JsonObject expectedResult = new JsonObject();

        // Run the test
        final JsonObject result = filtreUnderTest.filtreAllExceptFirestation(fireStationList, "ListAllExcept");

        // Verify the results

    }
}
