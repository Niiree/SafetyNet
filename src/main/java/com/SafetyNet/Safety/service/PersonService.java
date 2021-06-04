package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    /*public List<Person> findChildByAdresse(){
    }*/

    /*
    * @param    Ville
    * @return   Retourne une liste d'email en fonction de la ville
    *
    */
    public List<String> emailByCity(String city){
        return persons.stream().filter(user -> city.equals(user.getCity())).map(Person::getEmail).collect(Collectors.toList());
    }
    public void personSave(Person person) {
        persons.add(person);
    }



}
