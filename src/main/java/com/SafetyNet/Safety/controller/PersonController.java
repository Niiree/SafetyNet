package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.service.PersonService;
import com.SafetyNet.Safety.model.Person;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.*;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    //Récuperation d'une liste de Person
    @GetMapping(value = "/personsInfo")
    public List<Person> listePersons(){
    return personService.findAll();
    }

    /*@GetMapping(value = "/personInfo/{id}")
    public Person personById(@PathVariable int id){
        return PersonDao.PersonfindById(id);
    }
*/
    @PostMapping(value = "/personInfo/save")
    public void personSave(@RequestBody Person person){
        personService.PersonSave(person);
    }

    @GetMapping(value ="/load")
    private <JsonArray> void test () throws URISyntaxException, IOException, JSONException {



    }


}
