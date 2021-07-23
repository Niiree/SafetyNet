package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
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

    @BeforeAll
    private static void setUp(){

        fireStationService = new FireStationService();

    }

    @BeforeEach
    private void setUpPerTest(){
        List<FireStation> listFirestations = new ArrayList<>();
        ArrayList<String> listAddress = new ArrayList<String>();

        FireStation fireStation1 = new FireStation(Collections.singletonList("address 1"),1);
        FireStation fireStation2 = new FireStation(Collections.singletonList("address 2"),2);

        listFirestations.add(fireStation1);
        listFirestations.add(fireStation2);
    }


    @Test
    @DisplayName("Recuperer toutes les firestations")
    public void findAll(){
        List<FireStation> firestations = fireStationService.findAll();

        assertEquals(firestations.get(0),1);
    }

    @Test
    @DisplayName("Récuperer une firestation par son ID")
    public void findById(){
        FireStation firestation = fireStationService.findById(1);
        assertEquals(firestation.getStation(),1);
    }

    @Test
    @DisplayName("Récuperer une firestation par son address")
    public void findByAddress(){

        List<FireStation> firestation = fireStationService.findByAddress("address Type");

    assertEquals(firestation.get(0).getAddress(),"address 1");
    }

    @Test
    @DisplayName("Enregistrement d'une firestation")
    public void save(){
        FireStation fireStation = new FireStation(Collections.singletonList("address 3"),3);
        fireStationService.save(fireStation);
        assertEquals(fireStationService.findById(3),fireStation);
    }

    @Test
    @DisplayName("Suppresion d'une firestation")
    public void remove(){
        FireStation fireStation = new FireStation(Collections.singletonList("address 2"),2);
        assertTrue(fireStationService.remove(fireStation));
        assertNull(fireStationService.findById(2));
    }

    @Test
    @DisplayName("Ajout d'une address")
    public void addAddress(){
        assertTrue(fireStationService.addAddress("address ajouté",1));
        assertEquals(fireStationService.findById(1).getAddress(),"address ajouté");
    }

    @Test
    @DisplayName("Mise à jour d'une firestation")
    public void update(){
        FireStation fireStation = new FireStation(Collections.singletonList("address 3"),3);
        fireStationService.save(fireStation);
        FireStation fireStationUpdate = new FireStation(Collections.singletonList("address 5"),3);
        assertTrue(fireStationService.update(fireStationUpdate,3));
        assertEquals(fireStationService.findById(3).getAddress(),"[address 5]");
    }

}
