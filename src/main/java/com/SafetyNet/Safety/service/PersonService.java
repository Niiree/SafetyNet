package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PersonService {

    public static List<Person>persons = new ArrayList<>();


    public List<Person> findAll() {
        return persons;
    }
    /*public Person PersonfindById(int id) {
        for(Person person : persons){
            if(person.getId() == id){
                return person;
            }
        }
        return null;
    }*/

    public void PersonSave(Person person) {
        persons.add(person);
    }
}
