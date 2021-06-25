package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.service.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String firestationPut(@RequestBody FireStation firestation, @PathVariable int id){
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
    public void firestationDelete(@RequestBody FireStation firestation){
        fireStationService.remove(firestation);
    }

    /*
    * URl retourne une liste des personnes par caserne de pompiers correspondantes
    */
    @GetMapping(value = "/firestation")
    public String firestation(@RequestParam int stationNumber ){

        FireStation firestation = fireStationService.findById(stationNumber);
        List<Person> person = personService.findAll();
        AtomicInteger adulte = new AtomicInteger();
        AtomicInteger child = new AtomicInteger();

        List<Person> personFirestation = person.stream()
                .filter(persons -> firestation.getAddress().contains(persons.getAddress()))
                .collect(Collectors.toList());
    
        JsonObject result = new JsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = new JsonArray();

        for (Person personk:personFirestation){
            jsonArray.add(gson.toJson(personk,Person.class));
            if(personk.isAdult()){
                adulte.getAndIncrement();
            }else {
                child.getAndIncrement();
            }


        }
        result.add("Person", gson.toJsonTree(personFirestation));
        //TODO APPLIQUER FILTRE SUR PERSON
        result.addProperty("adulte",adulte);
        result.addProperty("enfants",child);

        return result.toString();

    }

    /*
     * Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne depompiers
     */
    @GetMapping(value = "/phoneAlert")
    public List<String> phoneAlert(@RequestParam int firestation){
        //TODO A FAIRE
        return null;
    }

    /*
     * Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la casernede pompiers la desservant.
     */
    @GetMapping(value = "/fire")
    public List<String> fire(@RequestParam String address){
        //TODO A FAIRE
        return null;
    }

    /*
     * Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper lespersonnes par adresse.
     */
    @GetMapping(value = "/flood/stations")
    public List<String> flood(@RequestParam String addess){
        //TODO A FAIRE
        return null;
    }


    /*
    * Récuperation d'une liste de FireStations
    */
    @GetMapping(value = "/firestationAll")
    public List<FireStation> listeFireStation(){
        return fireStationService.findAll();
    }




}
