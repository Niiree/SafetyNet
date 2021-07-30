package com.SafetyNet.Safety.controller;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.service.*;

import com.SafetyNet.Safety.util.Filtre;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


//TODO Refactor à faire concernant l'id
@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;
    private PersonService personService = new PersonService();
    private Filtre filtre  = new  Filtre();


    /*
    * Save Firestation
    */
    @PostMapping(value = "/firestation")
    public void firestationPost(@RequestBody FireStation fireStation){
        fireStationService.save(fireStation);
    }

    /*
    * Put Firestation
    */
    @PutMapping(value = "/firestation/{id}")
    public String firestationPut(@RequestBody FireStation firestation, @PathVariable int id) throws JsonProcessingException {
              if(fireStationService.update(firestation,id)){
                  return "Mise à jour de la firestation "+id;
              }else{
                  return "La mise à jour n'a pas eu lieu ";
              }
              //TODO Vérifier si c'est une Firestation?


    }
    /*
    * Delete Firestation
    */
    @DeleteMapping(value = "/firestation")
    public ResponseEntity<?> firestationDelete(@RequestBody FireStation firestation){

        if(fireStationService.remove(firestation)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
     * Récuperation d'une liste de FireStations
     *
     */
    @GetMapping(value = "/firestationAll")
    public List<FireStation> listeFireStation(){ return fireStationService.findAll();
    }


    ///////////////////////////////
    /////          URLS       /////
    ///////////////////////////////

    /*
    * firestation?stationNumber=<station_number>
    * URl retourne une liste des personnes par caserne de pompiers correspondantes
    */
    @GetMapping(value = "/firestation",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> firestation(@RequestParam int stationNumber ) throws JsonProcessingException {
        FireStation firestation = fireStationService.findById(stationNumber);
        JsonObject result = personService.PersonByFirestation(firestation);
        if(result != null){
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);}
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    /*
     * Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la casernede pompiers la desservant.
     * TODO A FAIRE
     */
    @GetMapping(value = "/fire")
    public List<String> fire(@RequestParam String address){
        List<Person> personList = personService.findAll();
        personList.stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
        List<FireStation> firestations = fireStationService.findByAddress(address);
        //TODO RETURN FIRE + PERSONLIST Filtre
       return null;
    }

    /*
     * Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper lespersonnes par adresse.
     * TODO A FAIRE
     */
    @GetMapping(value = "/flood/stations")
    public List<String> flood(@RequestParam List<Integer> station_number){

        for (Integer id:station_number
             ) {
            FireStation firestation = fireStationService.findById(id);
            List<Person> personList = personService.findAll();
            personList.stream()
                    .filter(person -> firestation.getAddress().contains(person.getAddress()))
                    .collect(Collectors.toList());
        }

        //TODO RETURN A FINIR
        return null;
    }




}
