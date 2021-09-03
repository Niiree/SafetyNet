package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
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
        final FireStation fireStation = new FireStation(Arrays.asList("value"), 0);

        // Run the test
        final boolean result = fireStationServiceTest.save(fireStation);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testRemove() {
        // Setup
        final FireStation fireStation = new FireStation(Arrays.asList("value"), 0);

        // Run the test
        final boolean result = fireStationServiceTest.remove(fireStation);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testAddAddress() {
        // Setup
       // when(fireStationServiceUnderTest.findById(0)).thenReturn(new FireStation());
        // Run the test
        final boolean result = fireStationServiceTest.addAddress("address", 0);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testUpdate() {
        // Setup
        //TODO : Comment mock un stream
        final FireStation fireStations = new FireStation(Arrays.asList("value"), 0);

        // Run the test
        final boolean result = fireStationServiceTest.update(fireStations, 0);

        // Verify the results
        assertThat(result).isTrue();
    }
}
