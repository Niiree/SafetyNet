package com.SafetyNet.Safety.controller;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.service.*;

import com.SafetyNet.Safety.util.BuilderResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class FireStationController {

    private final static Logger logger = LogManager.getLogger("FirestationController") ;
    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private PersonService personService;

    private BuilderResponse builderResponse = new BuilderResponse();

    /*
     * Save Firestation
     */
    @PostMapping(value = "/firestation")
    public ResponseEntity<?> firestationPost(@RequestBody FireStation fireStation) {
        logger.info("Post /firestation");
        return builderResponse.responseBoolean(fireStationService.save(fireStation));
    }

    /*
     * Put Firestation
     */
    @PutMapping(value = "/firestation/{id}")
    public ResponseEntity<?> firestationPut(@RequestBody FireStation firestation, @PathVariable int id)  {
        logger.info("Put /Firestation with param id ");
        return builderResponse.responseBoolean(fireStationService.update(firestation,id));}

    /*
     * Delete Firestation
     */
    @DeleteMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> firestationDelete(@RequestBody FireStation firestation) {
        logger.info("Delete /Firestation");
        return builderResponse.responseBoolean(fireStationService.remove(firestation));
    }


    ///////////////////////////////
    /////          URLS       /////
    ///////////////////////////////

    /*
     * firestation?stationNumber=<station_number>
     * URl retourne une liste des personnes par caserne de pompiers correspondantes
     */
    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> firestation(@RequestParam int stationNumber)  {
        return builderResponse.customResponse(personService.personByFirestation(fireStationService.findById(stationNumber))); }


    /*
     * fire?address=<address>
     * URL doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la casernede pompiers la desservant.
     */
    @GetMapping(value = "/fire", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fire(@RequestParam String address)   {
        return  builderResponse.customResponse(personService.fire(address)); }

    /*
     * flood/stations?list<Integer> =station_number
     * Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper lespersonnes par adresse.
     */
    @GetMapping(value = "/flood/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> flood(@RequestParam List<Integer> station_number)   {
        return builderResponse.customResponse(personService.flood(station_number)); }


}
