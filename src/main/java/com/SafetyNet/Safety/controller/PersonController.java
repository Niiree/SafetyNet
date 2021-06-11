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
    public void personCreate(@RequestBody Person person){
        personService.personSave(person);
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public void personUpdate(@PathVariable String firstName,String lastName,@RequestBody Person person){
        personService.personUpdate(firstName,lastName,person);
        //TODO Boolean réponse à rajouter partout
    }

    /*
    Suppresion d'un utilisateur en fonction de son firstname et lastname
    @Param Nom et prénom
     */
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
    public List<String> communityEmail(@RequestParam String city){
    return personService.emailByCity(city);
    }

    @GetMapping(value = "/personInfo/Name/{lastName}")
    public List<Person> personInfoName(@PathVariable String lastName){ return personService.personByName(lastName);
    }


}
