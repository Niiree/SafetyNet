package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.util.exceptions.PersonIntrouvableException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PersonService {

    private static List<Person> personsList = new ArrayList<>();

    public void personSave(Person person) {
        personsList.add(person);
    }

    public void personDelete(String firstName, String lastName){
        personsList.removeIf(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName()));
    }

    public void personUpdate(String firstName,String lastName, Person person){
        Optional<Person> user = personsList.stream().filter(p -> firstName.equals(p.getLastName()) && lastName.equals(p.getLastName())).findAny();
        if (user.isPresent()){
            //personsBDD.
        }
        //TODO A FINIR
    }

    public List<Person> findAll() { return personsList; }

    public Person findByFirstNameLastName(String firstName, String lastName){
        Person user = personsList.stream().filter(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())).
                findAny().orElse(null);
        if (user == null) throw new PersonIntrouvableException("L'utilisateur n'existe pas");
        return  user;
    }

    /*
     @param    Ville
     @return   Retourne une liste d'email en fonction de la ville
    */
    public List<String> emailByCity(String city){
        return personsList.stream().filter(user -> city.equals(user.getCity()))
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }
    /*
    @param  Name
    @return Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doiventtoutes apparaître.
     */
    public List<Person> personByName(String name){
        return personsList.stream().filter(person -> name.equals(person.getLastName())).collect(Collectors.toList());
    }
}
