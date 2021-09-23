package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FireStationServiceTest {

    private FireStationService fireStationServiceTest;
    private FireStation fir = new FireStation();

    @BeforeEach
    void setUp() {
        fireStationServiceTest = new FireStationService();

        ArrayList<String> address = new ArrayList<>();
        address.add("address1");
        fir.setAddress(address);
        fir.setStation(0);
        fireStationServiceTest.save(fir);
    }

    @Test
    @DisplayName("Récuperation toutes firestations FindAll")
    void testFindAll() {
        // Run the test
        final List<FireStation> result = fireStationServiceTest.findAll();

        // Verify the results
        assertThat(result.get(0)).isEqualTo(fir);
    }

    @Test
    void testFindById() {

        // Run the test
        final FireStation result = fireStationServiceTest.findById(0);

        // Verify the results
        assertThat(result.getAddress().get(0)).isEqualTo("address1");
        assertThat(result.getStation()).isEqualTo(0);

    }

    @Test
    void testFindByAddress() {
        // Run the test
        final List<FireStation> result = fireStationServiceTest.findByAddress("address1");

        // Verify the results
        assertThat(result.get(0).getAddress().get(0)).isEqualTo("address1");
        assertThat(result.get(0).getStation()).isEqualTo(0);

    }

    @Test
    void testSave() {
        // Setup
        final FireStation fireStation = new FireStation(Arrays.asList("test"), 2);

        // Run the test
        final boolean result = fireStationServiceTest.save(fireStation);


        // Verify the results
        assertThat(fireStationServiceTest.findById(2)).isEqualTo(fireStation);
        assertThat(result).isTrue();
    }

    @Test
    void testRemove() {
        // Setup
        final FireStation fireStation = new FireStation(Arrays.asList("value"), 0);

        // Run the test
        final boolean result = fireStationServiceTest.remove(fireStation);
        FireStation r = fireStationServiceTest.findById(0);

        // Verify the results
        assertThat(r).isNull();
        assertThat(result).isTrue();
    }

    @Test
    void testAddAddress() {
        // Setup

        // Run the test
        final boolean result = fireStationServiceTest.addAddress("address2", 0);

        // Verify the results
        assertThat(fireStationServiceTest.findById(0).getAddress()).isEqualTo(Arrays.asList("address1","address2"));
        assertThat(result).isTrue();
    }

    @Test
    void testUpdate() {
        // Setup
        final FireStation fireStations = new FireStation(Arrays.asList("test"), 0);

        assertThat(fireStationServiceTest.findById(0).getAddress()).isEqualTo(Arrays.asList("address1"));
        // Run the test
        final boolean result = fireStationServiceTest.update(fireStations, 0);

        // Verify the results
        assertThat(fireStationServiceTest.findById(0).getAddress()).isEqualTo(Arrays.asList("test"));
        assertThat(result).isTrue();


    }
}
