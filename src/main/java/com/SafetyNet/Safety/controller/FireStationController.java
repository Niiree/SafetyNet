package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.service.*;

import com.SafetyNet.Safety.util.ServiceDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


//TODO Refactor à faire concernant l'id
@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;
    private PersonService personService = new PersonService();
    private ServiceDate serviceDate = new ServiceDate();


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
    public void firestationPut(@RequestBody FireStation firestation, @PathVariable int id){
        //      fireStationService.updateFireStation(id);
        //TODO A FINIR
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
    public List<String> firestation(@RequestParam int stationNumber ){

        FireStation firestation = fireStationService.findById(stationNumber);
        List<Person> person = personService.findAll();
        AtomicInteger adulte = new AtomicInteger();
        AtomicInteger child = new AtomicInteger();

        List<Person> personFirestation = person.stream()
                .filter(persons -> firestation.getAddress().contains(persons.getAddress()))
                .collect(Collectors.toList());
    
        List<String> result = new ArrayList<>();
        
        for (Person p:personFirestation
             ) {
                result.add(p.getLastName()+" "+p.getFirstName()+" "+p.getAddress()+" "+p.getPhone());
                if(serviceDate.isAdult(p.getBirthdate())) {
                    adulte.getAndIncrement();
                }else{
                    child.getAndIncrement();
                }

        }
        result.add(adulte + " Adultes");
        result.add(child + " enfants");
        return result;

    }





    /*
    * Récuperation d'une liste de FireStations
    */
    @GetMapping(value = "/firestationAll")
    public List<FireStation> listeFireStation(){
        return fireStationService.findAll();
    }


}
