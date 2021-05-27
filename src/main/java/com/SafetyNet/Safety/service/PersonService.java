package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PersonService {

    private static List<Person>persons = new ArrayList<>();

    public List<Person> findAll() {
        return persons;
    }

    public void personSave(Person person) {
        persons.add(person);
    }
}
