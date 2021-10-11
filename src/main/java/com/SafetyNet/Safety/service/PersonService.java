package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.util.Filtre;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class PersonService {

    private final static Logger logger = LogManager.getLogger("PersonService") ;
    @Autowired
    private FireStationService fireStationService;

    private Filtre filtre = new Filtre();
    private List<Person> personsList = new ArrayList<>();


    public boolean personSave(Person person) {
        if (person != null) {
            if(person.getFirstName()!=null && person.getLastName()!=null){
                personsList.add(person);
                return true;
            }
        }
        logger.error("Sauvegarde de la person impossible");
        return false;
    }

    public boolean personDelete(String firstName, String lastName) {
        if (findByFirstNameLastName(firstName, lastName) != null) {
            personsList.removeIf(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName()));
            return true;
        } else {
            logger.error("Impossible de supprimer "+firstName +" "+ lastName);
           return false;
        }
    }


    public boolean personUpdate(Person person) {
        Optional<Person> user = personsList.stream().filter(p -> person.getLastName().equals(p.getLastName()) && person.getFirstName().equals(p.getFirstName())).findAny();
        if (user.isPresent()) {
            if (person.getBirthdate()!=null){
                user.get().setBirthdate(person.getBirthdate());
            }
            if (person.getMedical()!=null){
                user.get().setMedical(person.getMedical());
            }
            if (person.getAllergies()!=null){
                user.get().setAllergies(person.getAllergies());
            }
            if (person.getAddress()!=null){
                user.get().setAddress(person.getAddress());
            }
            if (person.getCity()!=null){
                user.get().setCity(person.getCity());
            }
            if (person.getPhone()!=null){
                user.get().setPhone(person.getPhone());
            }
            if (person.getEmail()!=null){
                user.get().setEmail(person.getEmail());
            }
            if (person.getZip()!=null){
                user.get().setZip(person.getZip());
            }
            return true;
        } else {
            logger.error("Update impossible");
            return false;
        }
    }


    public boolean personMedicalDelete(String firstName,String lastName){
        Person user = findByFirstNameLastName(firstName,lastName);
        if(user!= null){
            user.setMedical(null);
            user.setAllergies(null);
            user.setBirthdate(null);
            return true;
        }
        return false;
    }

    public boolean personMedicalPost(Person person){
        Optional<Person> user = personsList.stream().filter(p -> person.getLastName().equals(p.getLastName()) && person.getFirstName().equals(p.getFirstName())).findAny();

        if(user != null && user.get().getMedical() ==null && user.get().getAllergies()==null && user.get().getBirthdate()==null){
            user.get().setMedical(person.getMedical());
            user.get().setAllergies(person.getAllergies());
            user.get().setBirthdate(person.getBirthdate());
            return true;
        }
        return false;
    }

    public boolean personMedicalPut(Person person){
        Person user = findByFirstNameLastName(person.getFirstName(),person.getLastName());
        if(user != null){
            user.setMedical(person.getMedical());
            user.setAllergies(person.getAllergies());
            user.setBirthdate(person.getBirthdate());
            return true;
        }
        return false;
    }

    public List<Person> findAll() {
        return personsList;
    }

    public Person findByFirstNameLastName(String firstName, String lastName) {
        Person user = personsList.stream().filter(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName())).
                findAny().orElse(null);
        if (user == null) return null;
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
    * personInfo?firstName=<firstName>&lastName=<lastName>
    @param  Name
    @return Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent toutes apparaître.
     */
    public String personByName(String firstName, String lastName) {
        List<Person> personlist = personsList.stream().filter(person -> lastName.equals(person.getLastName()) & firstName.equals(person.getFirstName())).collect(Collectors.toList());

        if (personlist.size() != 0) {
            return  filtre.filtreAllExceptListPerson(personlist, "lastName", "address", "birthdate", "email", "medical", "allergie").get("value").getAsJsonArray().get(0).getAsJsonObject().toString();
        } else {
            logger.error("Person introuvable" + firstName +" "+ lastName);
            return null;
        }
    }

    /*
    * childAlert?address=<address>
     @param (String Address)
     @return doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     */
    public String childAlert(String address)  {
        List<Person> child = personsList.stream().filter(person -> person.isAdult()!= null && !person.isAdult() && person.getAddress().equals(address)).collect(Collectors.toList());
        if(child.isEmpty()){
            return null;
        }else {
            JsonObject result = new JsonObject();
            JsonObject jsonObject = filtre.filtreAllExceptListPerson(child, "address", "email", "city", "zip", "allergies", "birthdate", "zip", "medical", "adult");

            result.add("Person", jsonObject.get("value"));
            return result.toString();
        }
    }

    /*
    * firestation?stationNumber=<station_number>
    @param Firestation
    @return Json d'une liste de person trié en fonction de l'adresse de la firestation
    */
    public String personByFirestation(FireStation firestation) {
        AtomicInteger adulte = new AtomicInteger();
        AtomicInteger child = new AtomicInteger();
        List<Person> personFirestation = personsList.stream()
                .filter(persons -> firestation.getAddress().contains(persons.getAddress()))
                .collect(Collectors.toList());

        if (personFirestation.size() != 0) {
            for (Person pers : personFirestation) {
                if (pers.isAdult() ==null) {
                    logger.info(pers.getFirstName()+ " "+ pers.getLastName() + "ne possède pas de date de naissance");
                } else if (pers.isAdult()) {
                    adulte.getAndIncrement();
                }else{
                    child.getAndIncrement();
                }
            }

            JsonObject result = new JsonObject();
            result.add("Person", filtre.filtreAllExceptListPerson(personFirestation, "lastName", "firstName", "address", "phone").get("value"));
            result.addProperty("adulte", adulte);
            result.addProperty("enfants", child);

            return result.toString();
        } else {
            return null;
        }
    }

    /*
    * phoneAlert?firestation=<firestation_number>
    @param Firestation
    @return doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de pompiers
    */
    public String phoneAlert(FireStation firestation) {
        JsonObject result = new JsonObject();
        if (firestation != null) {
            List<Person> personList = personsList.stream()
                    .filter(persons -> firestation.getAddress().contains(persons.getAddress()))
                    .collect(Collectors.toList());
            if (personList.size() != 0) {

                List<String> listPhone = new ArrayList<>();
                for (Person per : personList) {
                    listPhone.add(per.getPhone());
                }
                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();
                JsonElement json = jsonParser.parse(gson.toJson(listPhone));
                result.add("Phone", json);
                return result.toString();
            } else {
                return null;
            }
        }else {
            logger.error("Aucune firestation n'est renseignée");
            return null;
        }
    }

    /*
    * CommunityEmail?city=<city>
    @param String City
    @return Cette url doit retourner les adresses mail de tous les habitants de la ville.
     */
    public List<String> communityEmail(String city) {

        List<Person> personList = personsList.stream().filter(person -> person.getCity().equals(city)).collect(Collectors.toList());
        List<String> listEmail = new ArrayList<>();
        if (personList.size() != 0) {
            for (Person person : personList
            ) {
                listEmail.add(person.getEmail());
            }
            return listEmail;
        } else {
           return null;
        }
    }

    /*
    * fire?address=<address>
    @param String address
    @return La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents médicaux (médicaments, posologie et allergies) de chaque personne.
     */
    public String fire(String address)  {
        List<Person> personList = personsList.stream().filter(person -> person.getAddress().equals(address)).collect(Collectors.toList());
        if (personList.size() != 0) {
            List<FireStation> firestations = fireStationService.findByAddress(address);
            JsonObject result = new JsonObject();
            result.add("Person", filtre.filtreAllExceptListPerson(personList, "firstName", "address", "phone").get("value"));
            result.add("Firestation", filtre.filtreAllExceptListFirestation(firestations, "station").get("value"));
            return result.toString();
        } else {
            return null;
        }
    }

    /*
    * flood/stations?stations=<a list of station_numbers>
    @param Liste ID des Firestations
    @return une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les personnes par adresse.
    */
    public String flood(List<Integer> station_number)  {
        List<FireStation> fireStationList = new ArrayList<>();
        for (int id : station_number) {
            FireStation fire = fireStationService.findById(id);
            if (fire != null) {
                fireStationList.add(fire);
            }
        }
        JsonObject result = new JsonObject();
        for (FireStation firestation : fireStationList) {
            result.add("Firestation" + firestation.getStation(), filtre.filtreAllExceptFirestation(firestation, "station").get("value"));

            // Pour chaque address, on récupere une liste de person
            for (String address : firestation.getAddress()) {
                List<Person> resultStream;
                resultStream = personsList.stream()
                        .filter(person -> firestation.getAddress().contains(person.getAddress()))
                        .collect(Collectors.toList());
                if (resultStream.size()!=0) {
                    result.add("Utilisateur " + firestation.getStation(), filtre.filtreAllExceptListPerson(resultStream, "lastName", "firstName", "phone", "birthdate").get("value"));
                }else {
                    return null;
                }
            }
        }
        return result.toString();
    }
}
