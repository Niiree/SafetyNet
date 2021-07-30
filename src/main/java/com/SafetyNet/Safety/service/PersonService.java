package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.util.Filtre;
import com.SafetyNet.Safety.util.exceptions.PersonIntrouvableException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.*;
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

    public boolean personDelete(String firstName, String lastName) {
        if (findByFirstNameLastName(firstName, lastName) != null) {
            personsList.removeIf(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName()));
            return true;
        } else {
            throw new PersonIntrouvableException("L'utilisateur n'existe pas");
        }
    }

    public boolean personUpdate(Person person) {
        Optional<Person> user = personsList.stream().filter(p -> person.getLastName().equals(p.getLastName()) && person.getFirstName().equals(p.getFirstName())).findAny();
        if (user.isPresent()) {
            user.get().setCity(person.getCity());
            user.get().setZip(person.getZip());
            user.get().setPhone(person.getPhone());
            user.get().setAddress(person.getAddress());
            user.get().setBirthdate(person.getBirthdate());
            user.get().setEmail(person.getEmail());
            return true;
        } else {
            throw new PersonIntrouvableException("L'utilisateur n'existe pas");

        }
    }

    public List<Person> findAll() {
        return personsList;
    }

    public Person findByFirstNameLastName(String firstName, String lastName) {
        Person user = personsList.stream().filter(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())).
                findAny().orElse(null);
        if (user == null) throw new PersonIntrouvableException("L'utilisateur n'existe pas");
        return user;
    }


    ///////////////////////////////
    /////          URLS       /////
    ///////////////////////////////


    /*
     @param    Ville
     @return   Retourne une liste d'email en fonction de la ville
    */
    public List<String> emailByCity(String city) {
        return personsList.stream().filter(user -> city.equals(user.getCity()))
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }

    /*
    @param  Name
    @return Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent toutes apparaître.
     */
    public List<Person> personByName(String name) {
        return personsList.stream().filter(person -> name.equals(person.getLastName())).collect(Collectors.toList());
    }

    /*
    * childAlert?address=<address>
     @param (String Address)
     @return doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     */
    public JsonObject childAlert(String address) throws JsonProcessingException {
        List<Person> child = personsList.stream().filter(person -> !person.isAdult() && person.getAddress().equals(address)).collect(Collectors.toList());
        JsonObject result = new JsonObject();
        JsonObject jsonObject = filtre.filtreAllExceptListPerson(child, "address", "email", "city", "zip", "allergies", "birthdate", "zip", "medical", "adult");

        result.add("Person", jsonObject.get("value"));
        return result;
    }

    /*
    * firestation?stationNumber=<station_number>
    @param Firestation
    @return Json d'une liste de person trié en fonction de l'adresse de la firestation
    */
    public JsonObject PersonByFirestation(FireStation firestation) throws JsonProcessingException {
        AtomicInteger adulte = new AtomicInteger();
        AtomicInteger child = new AtomicInteger();
        List<Person> personFirestation = personsList.stream()
                .filter(persons -> firestation.getAddress().contains(persons.getAddress()))
                .collect(Collectors.toList());

        if (personFirestation != null) {

            for (Person pers : personFirestation) {
                if (pers.isAdult()) {
                    adulte.getAndIncrement();
                } else {
                    child.getAndIncrement();
                }
            }

            JsonObject result = new JsonObject();
            result.add("Person", filtre.filtreAllExceptListPerson(personFirestation, "lastName", "firstName", "address", "phone").get("value"));
            result.addProperty("adulte", adulte);
            result.addProperty("enfants", child);

            return result;
        } else {
            throw new PersonIntrouvableException("Information manquantes"); // TODO ???
        }
    }

    /*
    * phoneAlert?firestation=<firestation_number>
    @param Firestation
    @return doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers
    */
    public JsonObject phoneAlert(FireStation firestation) throws JsonProcessingException {
        if (personsList != null) {
            List<Person> personList = personsList.stream()
                    .filter(persons -> firestation.getAddress().contains(persons.getAddress()))
                    .collect(Collectors.toList());
            JsonObject result = new JsonObject();
            List<String> listPhone = new ArrayList<>();

            for (Person per : personList) {
                listPhone.add(per.getPhone());
            }

            Gson gson = new Gson();
            JsonParser jsonParser = new JsonParser();
            JsonElement json = jsonParser.parse(gson.toJson(listPhone));
            result.add("Phone", json);

            return result;
        } else {
            throw new PersonIntrouvableException("Aucune utilisateur trouvé à cette address");
        }
    }

    public List<String> communityEmail(String city) {
        List<Person> personList = personsList.stream().filter(person -> person.getCity().equals(city)).collect(Collectors.toList());
        List<String> listEmail = new ArrayList<>();
        for (Person person : personList
        ) {
            listEmail.add(person.getEmail());
        }
        return listEmail;
    }

    public void fire() {
        //TODO A FAIRE
    }

}
