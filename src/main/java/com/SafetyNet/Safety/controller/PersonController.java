package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.repository.PersonRepository;
import com.SafetyNet.Safety.model.Person;
import com.google.gson.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.Charset;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository PersonDao;

    //Récuperation d'une liste de Person
    @GetMapping(value = "/personsInfo")
    public List<Person> listePersons(){
    return PersonDao.findAll();
    }

    /*@GetMapping(value = "/personInfo/{id}")
    public Person personById(@PathVariable int id){
        return PersonDao.PersonfindById(id);
    }
*/
    @PostMapping(value = "/personInfo/save")
    public void personSave(@RequestBody Person person){
        PersonDao.PersonSave(person);
    }

    @GetMapping(value ="/load")
    private <JsonArray> void test () throws URISyntaxException, IOException, JSONException {



    }


}
