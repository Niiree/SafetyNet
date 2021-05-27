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

    public void personSave(Person person){ personService.personSave(person); }


}
