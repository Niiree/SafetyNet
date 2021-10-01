package com.SafetyNet.Safety.util;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FiltreTest {

    private Filtre filtreUnderTest;

    private List<Person> personList = Arrays.asList(new Person("firstName", "lastName", "address", "test", "zip", "phone", "email"));
    private FireStation firestation = new FireStation();


    @BeforeEach
    void setUp() {
        filtreUnderTest = new Filtre();
    }

    @Test
    void testFiltreAllExceptListPerson() {
        // Setup
        String expectedd = "[{\"city\":\"test\"}]";

        // Run the test
         JsonObject result = filtreUnderTest.filtreAllExceptListPerson(personList, "city");

        // Verify the results
        assertThat(result.get("value").toString()).isEqualTo(expectedd);
    }

    @Test
    void testFiltreAllExceptListFirestation() {
        // Setup
         List<FireStation> fireStationList = Arrays.asList(new FireStation(Arrays.asList("value"), 0));
         JsonObject expectedResult = new JsonObject();

        // Run the test
         JsonObject result = filtreUnderTest.filtreAllExceptListFirestation(fireStationList, "ListAllExcept");

        // Verify the results

    }

    @Test
    void testFiltreAllExceptFirestation() {
        // Setup
        firestation.setStation(1);
        firestation.setAddress(Arrays.asList("Test"));

         String expectedResult =  "{\"station\":1}";

        // Run the test
         JsonObject result = filtreUnderTest.filtreAllExceptFirestation(firestation, "station");

        // Verify the results
        assertThat(result.get("value").toString()).isEqualTo(expectedResult);

    }
}
