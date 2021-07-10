package com.SafetyNet.Safety.controller;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.service.*;

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
     * Récuperation d'une liste de FireStations
     *
     */
    @GetMapping(value = "/firestationAll")
    public List<FireStation> listeFireStation(){
        return fireStationService.findAll();
    }


    ///////////////////////////////
    /////          URLS       /////
    ///////////////////////////////

    /*
    * URl retourne une liste des personnes par caserne de pompiers correspondantes
    * TODO Appliquer un filtre sur le resultat + trow error
    */
    @GetMapping(value = "/firestation",produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> firestation(@RequestParam int stationNumber ) throws JsonProcessingException {
        System.out.println("ok");
        FireStation firestation = fireStationService.findById(stationNumber);
        List<Person> person = personService.findAll();
        AtomicInteger adulte = new AtomicInteger();
        AtomicInteger child = new AtomicInteger();

        List<Person> personFirestation = person.stream()
                .filter(persons -> firestation.getAddress().contains(persons.getAddress()))
                .collect(Collectors.toList());

        //Initialisation du filtre
        SimpleBeanPropertyFilter filtreUrl = SimpleBeanPropertyFilter.serializeAllExcept("email","birthdate","allergies","medical","adult","phone");
        FilterProvider list = new SimpleFilterProvider().addFilter("Filtre",filtreUrl);
        MappingJacksonValue personsfiltre  = new MappingJacksonValue(personFirestation);
        personsfiltre.setFilters(list);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(list);

        String jsonData = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(personsfiltre);
        System.out.println(jsonData);

        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();

        //     JsonObject result = new JsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = new JsonArray();


        for (Person pers:personFirestation){
            jsonArray.add(gson.toJson(pers,Person.class));
            if(pers.isAdult()){
                adulte.getAndIncrement();
            }else {
                child.getAndIncrement();
            }
        }

        JsonObject result = new JsonObject();
        result.add("Person", jsonObject.get("value"));
        //TODO Ajouter enfant + adults
       result.addProperty("adulte",adulte);
       result.addProperty("enfants",child);

       return new ResponseEntity<>(result.toString(), HttpStatus.ACCEPTED);
     //   return result.toString();
    }

    /*
     * Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne depompiers
     * TODO A FAIRE
     */
    @GetMapping(value = "/phoneAlert")
    public MappingJacksonValue phoneAlert(@RequestParam int firestation_number){
        FireStation fire = fireStationService.findById(firestation_number);
        List<Person> personList = personService.findAll();

        List<Person> personNum = personList.stream()
                .filter(persons -> fire.getAddress().contains(persons.getAddress()))
                .collect(Collectors.toList());

        //TODO APPLIQUER LE FILTRE

        SimpleBeanPropertyFilter filtreUrl = SimpleBeanPropertyFilter.serializeAllExcept("email","birthdate","allergies","medical","adult","firstName","lastName");
        FilterProvider list = new SimpleFilterProvider().addFilter("Filtre",filtreUrl);
        MappingJacksonValue listFiltre  = new MappingJacksonValue(personNum);
        listFiltre.setFilters(list);

        return listFiltre;
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
