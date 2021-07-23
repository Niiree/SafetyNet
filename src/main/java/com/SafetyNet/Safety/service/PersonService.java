package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.util.Filtre;
import com.SafetyNet.Safety.util.exceptions.PersonIntrouvableException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class PersonService {

    private Filtre filtre = new Filtre();
    private static final List<Person> personsList = new ArrayList<>();

    public void personSave(Person person) {
        personsList.add(person);
    }

    public boolean personDelete(String firstName, String lastName){
        if (findByFirstNameLastName(firstName, lastName) != null){
            personsList.removeIf(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName()));
            return true;
        }else{
            throw new PersonIntrouvableException("L'utilisateur n'existe pas");
        }
    }

    public boolean personUpdate(Person person){
        Optional<Person> user = personsList.stream().filter(p -> person.getLastName().equals(p.getLastName()) && person.getFirstName().equals(p.getFirstName())).findAny();
        if (user.isPresent()){
            user.get().setCity(person.getCity()) ;
            user.get().setZip(person.getZip());
            user.get().setPhone(person.getPhone());
            user.get().setAddress(person.getAddress());
            user.get().setBirthdate(person.getBirthdate());
            user.get().setEmail(person.getEmail());
            return true;
        }else{
             throw new PersonIntrouvableException("L'utilisateur n'existe pas");

        }
        //TODO RETURN ERROR A FAIRE
    }

    public List<Person> findAll() {
        return personsList; }

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
    @return Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent toutes apparaître.
     */
    public List<Person> personByName(String name){
        return personsList.stream().filter(person -> name.equals(person.getLastName())).collect(Collectors.toList());
    }

    /*
     * @return doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     */
    public List<Person> childAlert(String address){
        return personsList.stream().filter(person -> !person.isAdult() && person.getAddress().equals(address)).collect(Collectors.toList());
    }

    /*
    @param Firestation
    @return Json d'une liste de person trié en fonction de l'adresse de la firestation
    */
    public JsonObject PersonByFirestation(FireStation firestation) throws JsonProcessingException {


        AtomicInteger adulte = new AtomicInteger();
        AtomicInteger child = new AtomicInteger();
        List<Person> personFirestation = personsList.stream()
                .filter(persons -> firestation.getAddress().contains(persons.getAddress()))
                .collect(Collectors.toList());

        JsonObject jsonObject = filtre.filtreListPerson(personFirestation, "email");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = new JsonArray();


        for (Person pers:personFirestation){
            jsonArray.add(gson.toJson(pers,Person.class));
            if(pers.isAdult()){
                adulte.getAndIncrement();
            }else {
                child.getAndIncrement();
            }
        }
        JsonObject result = new JsonObject();
        result.add("Person", jsonObject.get("value"));
        result.addProperty("adulte",adulte);
        result.addProperty("enfants",child);

        return result;
    }

    public JsonObject phoneAlert(FireStation firestation) throws JsonProcessingException {
        List<Person> personList = personsList.stream()
                .filter(persons -> firestation.getAddress().contains(persons.getAddress()))
                .collect(Collectors.toList());

        JsonObject result = filtre.filtreListPerson(personList, "email");
        return result;

    }

    public List<String> communityEmail(String city){
        List<Person> personList = personsList.stream().filter(person -> person.getCity().equals(city)).collect(Collectors.toList());
        List<String>listEmail = new ArrayList<>();
        for (Person person:personList
        ) {
            listEmail.add(person.getEmail());
        }
        return listEmail;
    }

    public void fire (){
        //TODO A FAIRE
    }

}
