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
    @GetMapping(value = "/personInfo")
    public List<Person> listePersons(){ return personService.findAll(); }


    @PostMapping(value = "/persons")
    public void createPerson(@RequestBody Person person){
        personService.personSave(person);
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public void putPerson( ){
    }

    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public void personDelete(@PathVariable String firstName,@PathVariable String lastName){
        personService.personDelete(firstName,lastName);
    }


    @GetMapping(value = "/childAlert")
    public List<Person> childAlert(){
        //todo
        return null;
    }

    @GetMapping (value = "/communityEmail")
    public List<String> communityEmail(){
        //TODO passage en param de la ville
    return personService.emailByCity("Culver");
    }

    @GetMapping(value = "/personInfo/Name/{lastName}")
    public List<Person> personInfoName(@PathVariable String lastName){
        return personService.personByName(lastName);
    }

    public void addPerson(@RequestBody Person person){ personService.personSave(person); }


}
