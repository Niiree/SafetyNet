package com.SafetyNet.Safety.Archive;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.service.FireStationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FireStationServiceTest {

    private static FireStationService fireStationService;
    private FireStation fireStation1;
    private FireStation fireStation2;

    @BeforeAll
    private static void setUp() {

    }

    @BeforeEach
    private void setUpPerTest() {
        fireStationService = new FireStationService();
        fireStation1 = new FireStation(Collections.singletonList("address 1"), 1);
        fireStation2 = new FireStation(Collections.singletonList("address 2"), 2);

    }


    @Test
    @DisplayName("Recuperer toutes les firestations")
    public void findAll() {
        fireStationService.save(fireStation1);
        fireStationService.save(fireStation2);

        List<FireStation> firestations = fireStationService.findAll();


        assertEquals(firestations.get(0).getStation(), fireStation1.getStation());
        assertEquals(firestations.get(1).getStation(), fireStation2.getStation());
    }

    @Test
    @DisplayName("Récuperer une firestation par son ID")
    public void findById() {
        fireStationService.save(fireStation1);

        FireStation firestation = fireStationService.findById(1);
        assertEquals(firestation.getStation(), 1);
    }

    @Test
    @DisplayName("Récuperer une firestation par son address")
    public void findByAddress() {
        fireStationService.save(fireStation1);

        List<FireStation> firestation = fireStationService.findByAddress("address 1");
        assertEquals(firestation.get(0).getAddress(), fireStation1.getAddress());
    }

    @Test
    @DisplayName("Enregistrement d'une firestation")
    public void save() {
        FireStation fireStation = new FireStation(Collections.singletonList("address 3"), 3);
        fireStationService.save(fireStation);
        assertEquals(fireStationService.findById(3).getStation(), fireStation.getStation());
    }

    @Test
    @DisplayName("Suppresion d'une firestation")
    public void remove() {
        fireStationService.save(fireStation1);
        FireStation fireStation = new FireStation(Collections.singletonList("address 1"), 1);
        assertTrue(fireStationService.remove(fireStation));
        assertNull(fireStationService.findById(1));
    }

    @Test
    @DisplayName("Ajout d'une address")
    public void addAddress() {
        fireStationService.save(fireStation1);
        assertTrue(fireStationService.addAddress("address ajouté", 1));
        assertEquals(fireStationService.findById(1).getAddress(), fireStation1.getAddress());
    }

    @Test
    @DisplayName("Mise à jour d'une firestation")
    public void update() throws JsonProcessingException {
        fireStationService.save(fireStation1);
        List<String> updateAddress = new ArrayList<String>();
        updateAddress.add("address 5");

        FireStation fireStationUpdate = new FireStation(updateAddress, 1);
        assertTrue(fireStationService.update(fireStationUpdate, 1));
        assertEquals(fireStationService.findById(1).getAddress(), updateAddress);
    }

}
