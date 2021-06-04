package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.service.FireStationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    /*
    Récuperation d'une liste de FireStations
     */
    @GetMapping(value = "/firestationInfo")
    public List<FireStation> listeFireStation(){ return fireStationService.findAll(); }

    @PostMapping(value = "/firestation")
    public void firestationPost(){
        //TODO
    }
    @PutMapping(value = "/firestation")
    public void firestationPut(){
        //TODO
    }
    @DeleteMapping(value = "/firestation")
    public void firestationDelete(){
        //TODO
    }

    public void saveFireStation(FireStation fireStation){ fireStationService.saveFireStation(fireStation); }
}
