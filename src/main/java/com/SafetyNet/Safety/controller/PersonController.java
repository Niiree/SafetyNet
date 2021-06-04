package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.service.PersonService;
import com.SafetyNet.Safety.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    /*
    Récuperation d'une liste de Person
    */
    @GetMapping(value = "/personsInfo")
    public List<Person> listePersons(){ return personService.findAll(); }


    @PostMapping(value = "/person/")
    public void personPost(){

    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public void putPerson( ){

    }

    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public void personDelete(@PathVariable String firstname,@PathVariable String lastName){
        System.out.println(firstname + lastName);
    }


    @GetMapping(value = "/childAlert")
    public List<Person> childAlert(){
        //todo
        return null;
    }

    @GetMapping (value = "/communityEmail")
    public List<String> communityEmail(){
        //TODO passage en param de la ville
    return personService.emailAll("Culver");
    }

    public void addPerson(@RequestBody Person person){ personService.personSave(person); }


}
