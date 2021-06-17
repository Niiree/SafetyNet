package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.service.FireStationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//TODO Refactor à faire concernant l'id
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
    public void firestationPost(@RequestBody FireStation fireStation){
        fireStationService.save(fireStation);
    }
    @PutMapping(value = "/firestation/{id}")
    public void firestationPut(@RequestBody FireStation firestation, @PathVariable int id){
  //      fireStationService.updateFireStation(id);
    }
    @DeleteMapping(value = "/firestation")
    public void firestationDelete(@RequestBody FireStation firestation){
        fireStationService.remove(firestation);
    }



    public void saveFireStation(FireStation fireStation){ fireStationService.save(fireStation); }
}
