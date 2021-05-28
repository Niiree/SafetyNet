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

    public Person findByFirstNameLastName(String firstName, String lastName){
        return  persons.stream().filter(customer -> firstName.equals(customer.getFirstName()) && lastName.equals(customer.getLastName())).
        findAny()  .orElse(null);
    }

    public void personSave(Person person) {
        persons.add(person);
    }

}
