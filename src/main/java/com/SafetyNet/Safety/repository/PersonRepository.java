package com.SafetyNet.Safety.repository;

import com.SafetyNet.Safety.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class PersonDaoImpl   {

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
