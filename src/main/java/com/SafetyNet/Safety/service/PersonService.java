package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PersonService {

    private static List<Person> personsBDD = new ArrayList<>();

    public List<Person> findAll() {
        return personsBDD;
    }

    public Person findByFirstNameLastName(String firstName, String lastName){
        return  personsBDD.stream().filter(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())).
        findAny()  .orElse(null);
    }
    /*public List<Person> findChildByAdresse(){
    }*/

    /*
     @param    Ville
     @return   Retourne une liste d'email en fonction de la ville

    */
    public List<String> emailByCity(String city){
        return personsBDD.stream().filter(user -> city.equals(user.getCity()))
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }
    /*
    @param  Ville
    @return Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doiventtoutes apparaître.
     */
    public List<Person> personByName(String name){
        List<Person> test = personsBDD.stream().filter(person -> name.equals(person.getLastName())).collect(Collectors.toList());
        //TODO A FINIR
        return null;
    }

    public void personSave(Person person) {
        personsBDD.add(person);
    }

    public void personDelete(String firstName, String lastName){
        personsBDD.removeIf(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName()));
    }

    public void personUpdate(String firstName,String lastName, Person person){
        Optional<Person> user = personsBDD.stream().filter(p -> firstName.equals(p.getLastName()) && lastName.equals(p.getLastName())).findAny();
        if (user.isPresent()){
            //personsBDD.
        }
        //TODO A FINIR
    }



}
