package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.Person;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class PersonService {

    private static List<Person>persons = new ArrayList<>();

    public List<Person> findAll() {
        return persons;
    }

    public Person findByFirstNameLastName(String firstName, String lastName){
        return  persons.stream().filter(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())).
        findAny()  .orElse(null);
    }
    /*public List<Person> findChildByAdresse(){
    }*/

    /*
     @param    Ville
     @return   Retourne une liste d'email en fonction de la ville

    */
    public List<String> emailByCity(String city){
        return persons.stream().filter(user -> city.equals(user.getCity()))
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }
    /*
    @param  Ville
    @return Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doiventtoutes apparaître.
     */
    public List<Person> personByName(String name){
        List<Person> test = persons.stream().filter(person -> name.equals(person.getLastName())).collect(Collectors.toList());
        //TODO A FINIR
        return null;
    }

    public void personSave(Person person) {
        persons.add(person);
    }

    public void personDelete(String firstName, String lastName){
        persons.removeIf(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName()));
    }



}
