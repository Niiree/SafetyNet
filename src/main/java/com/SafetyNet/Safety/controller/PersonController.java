package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.dao.PersonDaoImpl;
import com.SafetyNet.Safety.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonDaoImpl PersonDao;

    //Récuperation d'une liste de Person
    @GetMapping(value = "/personsInfo")
    public List<Person> listePersons(){
    return PersonDao.findAll();
    }

    @GetMapping(value = "/personInfo/{id}")
    public Person personById(@PathVariable int id){
        return PersonDao.PersonfindById(id);
    }

    @PostMapping(value = "/personInfo/save")
    public void personSave(Person person){
        PersonDao.PersonSave(person);
    }

}
