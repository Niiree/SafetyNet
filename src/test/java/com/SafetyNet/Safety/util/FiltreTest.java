package com.SafetyNet.Safety.util;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.util.config.Filtre;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Filtre sur City pour Person")
    void testFiltreAllExceptListPerson() {
        // Setup
        String expectedd = "[{\"city\":\"test\"}]";

        // Run the test
        JsonObject result = filtreUnderTest.filtreAllExceptListPerson(personList, "city");

        // Verify the results
        assertThat(result.get("value").toString()).isEqualTo(expectedd);
    }

    @Test
    @DisplayName("Filtre sur station pour plusieurs firestations")
    void testFiltreAllExceptListFirestation() {
        // Setup
        List<FireStation> fireStationList = new ArrayList<>();
        fireStationList.add(new FireStation(Arrays.asList("test1"),1));
        fireStationList.add(new FireStation(Arrays.asList("test2"),2));
        String expectedResult = "[{\"station\":1},{\"station\":2}]";

        // Run the test
        JsonObject result = filtreUnderTest.filtreAllExceptListFirestation(fireStationList, "station");
        // Verify the results
        assertThat(result.get("value").toString()).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Filtre sur station pour un firestation")
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
