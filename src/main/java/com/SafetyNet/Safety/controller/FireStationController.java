package com.SafetyNet.Safety.controller;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.service.*;

import com.SafetyNet.Safety.util.BuilderResponse;
import com.SafetyNet.Safety.util.Filtre;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;
    @Autowired
    private PersonService personService;

    private BuilderResponse builderResponse = new BuilderResponse();
    private Filtre filtre = new Filtre();


    /*
     * Save Firestation
     */
    @PostMapping(value = "/firestation")
    public ResponseEntity<?> firestationPost(@RequestBody FireStation fireStation) {
        return builderResponse.ResponseBoolean(fireStationService.save(fireStation));
    }

    /*
     * Put Firestation
     */
    @PutMapping(value = "/firestation/{id}")
    public ResponseEntity<?> firestationPut(@RequestBody FireStation firestation, @PathVariable int id) throws JsonProcessingException {
        return builderResponse.ResponseBoolean(fireStationService.update(firestation,id));}

    /*
     * Delete Firestation
     */
    @DeleteMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> firestationDelete(@RequestBody FireStation firestation) {
        return builderResponse.ResponseBoolean(fireStationService.remove(firestation)); }

    /*
     * Récuperation d'une liste de FireStations
     *
     */
    @GetMapping(value = "/firestationAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listeFireStation() {
        return builderResponse.CustomResponse(fireStationService.findAll()); }


    ///////////////////////////////
    /////          URLS       /////
    ///////////////////////////////

    /*
     * firestation?stationNumber=<station_number>
     * URl retourne une liste des personnes par caserne de pompiers correspondantes
     */
    @GetMapping(value = "/firestation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> firestation(@RequestParam int stationNumber) throws JsonProcessingException {
        return builderResponse.CustomResponse(personService.personByFirestation(fireStationService.findById(stationNumber))); }


    /*
     * fire?address=<address>
     * URL doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la casernede pompiers la desservant.
     * UR OK
     */
    @GetMapping(value = "/fire", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fire(@RequestParam String address) throws JsonProcessingException {
        return  builderResponse.CustomResponse(personService.fire(address)); }

    /*
     * Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper lespersonnes par adresse.
     * URK OK
     */
    @GetMapping(value = "/flood/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> flood(@RequestParam List<Integer> station_number) throws JsonProcessingException {
        return builderResponse.CustomResponse(personService.flood(station_number)); }

}
