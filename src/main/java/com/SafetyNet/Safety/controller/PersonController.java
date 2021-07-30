package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.service.FireStationService;
import com.SafetyNet.Safety.service.PersonService;
import com.SafetyNet.Safety.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationService fireStationService;


    @PostMapping(value = "/person")
    public void personPost(@RequestBody Person person) {
        personService.personSave(person);
    }

    @PutMapping(value = "/person")
    public String personUpdate(@RequestBody Person person) {
        if (personService.personUpdate(person)) {
            return "Person mise à jour";
        }
        return "Update échoué";
    }

    /*
    Suppresion d'un utilisateur en fonction de son firstname et lastname
    @Param Nom et prénom
     */
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public String personDelete(@PathVariable String firstName, @PathVariable String lastName) {
        if (personService.personDelete(firstName, lastName)) {
            return "Person supprimé";
        } else {
            return "Person introuvable";
        }
    }

    /*
 Récuperation d'une liste de Person
  */
    @GetMapping(value = "/personList")
    public ResponseEntity<?> listePersons() {
        List<Person> listPerson = personService.findAll();
        if (listPerson != null) {
            return new ResponseEntity<>(listPerson, HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    ///////////////////////////////
    /////          URLS       /////
    ///////////////////////////////

    /*
     * URl doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     * URL OK
     */
    @GetMapping(value = "/childAlert",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> childAlert(@RequestParam String address) throws JsonProcessingException {
        JsonObject childAlert = personService.childAlert(address);
        if (childAlert != null) {
            return new ResponseEntity<>(childAlert.toString(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Aucun utilisateur trouvées", HttpStatus.NO_CONTENT);
        }
    }

    /*
     * URl doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doiventtoutes apparaître
     * URL OK
     */
    @GetMapping(value = "/personInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> personInfoName(@RequestParam String firstName, String lastName) throws JsonProcessingException {
        JsonObject person = personService.personByName(firstName,lastName);

        if (person != null) {
            return new ResponseEntity<>(person.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
     * URl doit retourner les adresses mail de tous les habitants de la ville
     *
     */
    @GetMapping(value = "/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> communityEmail(@RequestParam String city) {
        List<String> result = personService.communityEmail(city);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }


    /*
     * Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne depompiers
     * URK OK
     */
    @GetMapping(value = "/phoneAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> phoneAlert(@RequestParam int firestation_number) throws JsonProcessingException {
        FireStation fire = fireStationService.findById(firestation_number);
        JsonObject result = personService.phoneAlert(fire);
        if (result != null) {
            return new ResponseEntity<>(result.toString(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
