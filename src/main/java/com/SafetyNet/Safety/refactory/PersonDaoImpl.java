package com.SafetyNet.Safety.dao;

import com.SafetyNet.Safety.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class PersonDaoImpl implements PersonDao {

    public static List<Person>persons = new ArrayList<>();

    static {
        persons.add(new Person(1,"Nicolas","Le stunff","InfoAddresse","Ville","Zip","phone","email@email.com"));
    }

    @Override
    public List<Person> findAll() {
        return persons;
    }

    @Override
    public Person PersonfindById(int id) {
        for(Person person : persons){
            if(person.getId() == id){
                return person;
            }
        }
        return null;
    }

    @Override
    public void PersonSave(Person person) {
        persons.add(person);
    }
}
