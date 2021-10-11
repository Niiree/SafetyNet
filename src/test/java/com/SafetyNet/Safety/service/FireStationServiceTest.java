package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
import org.junit.jupiter.api.*;

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

    @AfterEach
    void setAfter(){
        List<FireStation> ok = fireStationServiceTest.findAll();
    }

    @Test
    @DisplayName("Récuperation toutes firestations FindAll")
    void testFindAll() {
        // Run the test
        List<FireStation> result = fireStationServiceTest.findAll();

        // Verify the results
        assertThat(result.get(0).getAddress()).isEqualTo(fir.getAddress());
        assertThat(result.get(0).getStation()).isEqualTo(fir.getStation());
    }

    @Test
    @DisplayName("Récuperation  firestation par ID")
    void testFindById() {

        // Run the test
        FireStation result = fireStationServiceTest.findById(0);

        // Verify the results
        assertThat(result.getAddress().get(0)).isEqualTo("address1");
        assertThat(result.getStation()).isEqualTo(0);
    }

    @Test
    @DisplayName("Récuperation  firestation par address")
    void testFindByAddress() {
        // Run the test
        List<FireStation> result = fireStationServiceTest.findByAddress("address1");

        // Verify the results
        assertThat(result.get(0).getAddress().get(0)).isEqualTo("address1");
        assertThat(result.get(0).getStation()).isEqualTo(0);
    }

    @Test
    @DisplayName("sauvegarde firestation")
    void testSave() {
        // Setup
        FireStation fireStation = new FireStation(Arrays.asList("test"), 2);

        // Run the test
        boolean result = fireStationServiceTest.save(fireStation);

        // Verify the results
        assertThat(fireStationServiceTest.findById(2)).isEqualTo(fireStation);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Delete firestation")
    void testRemove() {
        // Setup
        FireStation fireStation = new FireStation(Arrays.asList("value"), 0);

        // Run the test
        boolean result = fireStationServiceTest.remove(fireStation);
        FireStation r = fireStationServiceTest.findById(0);

        // Verify the results
        assertThat(r).isNull();
        assertThat(result).isTrue();
    }


    @Test
    @DisplayName("Ajout d'une address firestation")
    void testAddAddress() {
        // Run the test
        boolean result = fireStationServiceTest.addAddress("address2", 0);

        // Verify the results
        assertThat(fireStationServiceTest.findById(0).getAddress()).isEqualTo(Arrays.asList("address1","address2"));
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Update de firestation")
    void testUpdate() {
        // Setup
        FireStation fireStations = new FireStation(Arrays.asList("test"), 0);

        assertThat(fireStationServiceTest.findById(0).getAddress()).isEqualTo(Arrays.asList("address1"));
        // Run the test
        boolean result = fireStationServiceTest.update(fireStations, 0);

        // Verify the results
        assertThat(fireStationServiceTest.findById(0).getAddress()).isEqualTo(Arrays.asList("test"));
        assertThat(result).isTrue();
    }
}
