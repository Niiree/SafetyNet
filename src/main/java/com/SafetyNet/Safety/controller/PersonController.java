package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.service.PersonService;
import com.SafetyNet.Safety.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;



    @PostMapping(value = "/person")
    public void personPost(@RequestBody Person person){
        personService.personSave(person);
    }

    @PutMapping(value = "/person")
    public String personUpdate(@RequestBody Person person){
        if(personService.personUpdate(person)){
            return "Person mise à jour";
        }
        //TODO Boolean réponse à rajouter partout
        return "Update échoué";
    }



    /*
    Suppresion d'un utilisateur en fonction de son firstname et lastname
    @Param Nom et prénom
     */
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public void personDelete(@PathVariable String firstName,@PathVariable String lastName){
        personService.personDelete(firstName,lastName);
    }

    /*
 Récuperation d'une liste de Person
  */
    @GetMapping(value = "/personList")
    public ResponseEntity<?> listePersons(){
        return new ResponseEntity<>(personService.findAll(), HttpStatus.ACCEPTED); }



    ///////////////////////////////
    /////          URLS       /////
    ///////////////////////////////

    /*
     * URl doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     * URL OK
     */
    @GetMapping(value = "/childAlert")
    public List<Person> childAlert(@RequestParam String address){
        return personService.findAll().stream().filter(person -> !person.isAdult() && person.getAddress().equals(address)).collect(Collectors.toList());
    }

    /*
     * URl doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doiventtoutes apparaître
     * URL OK
     */
    @GetMapping(value = "/personInfo/Name/{lastName}")
    public List<Person> personInfoName(@PathVariable String lastName){
        return personService.personByName(lastName);
    }

    /*
     * URl doit retourner les adresses mail de tous les habitants de la ville
     * URL OK
     */
    @GetMapping (value = "/communityEmail")
    public List<String> communityEmail(@RequestParam String city){

        List<Person> personList = personService.findAll().stream().filter(person -> person.getCity().equals(city)).collect(Collectors.toList());
        List<String>listEmail = new ArrayList<>();
        for (Person person:personList
             ) {
            listEmail.add(person.getEmail());
        }
        return listEmail;
    }


}
