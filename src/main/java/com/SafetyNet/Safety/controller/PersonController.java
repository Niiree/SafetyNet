package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.service.FireStationService;
import com.SafetyNet.Safety.service.PersonService;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.util.BuilderResponse;
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

    private BuilderResponse builderResponse = new BuilderResponse();


    @PostMapping(value = "/person")
    public ResponseEntity<?> personPost(@RequestBody Person person) {
        return builderResponse.ResponseBoolean(personService.personSave(person)); }

    @PutMapping(value = "/person")
    public ResponseEntity<?> personUpdate(@RequestBody Person person) {
       return builderResponse.ResponseBoolean(personService.personUpdate(person)); }

    /*
    Suppresion d'un utilisateur en fonction de son firstname et lastname
    @Param Nom et prénom
     */
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<?> personDelete(@PathVariable String firstName, @PathVariable String lastName) {
    return builderResponse.ResponseBoolean(personService.personDelete(firstName,lastName)); }


    ///////////////////////////////
    /////          URLS       /////
    ///////////////////////////////

    /*
     * URl doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     * URL OK
     */
    @GetMapping(value = "/childAlert",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> childAlert(@RequestParam String address)  {
        return builderResponse.CustomResponse(personService.childAlert(address)); }

    /*
     * URl doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doiventtoutes apparaître
     * URL OK
     */
    @GetMapping(value = "/personInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> personInfoName(@RequestParam String firstName, String lastName) {
        return builderResponse.CustomResponse(personService.personByName(firstName,lastName));
    }
    /*
     * URl doit retourner les adresses mail de tous les habitants de la ville
     *
     */
    @GetMapping(value = "/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> communityEmail(@RequestParam String city) {
       return builderResponse.CustomResponse(personService.communityEmail(city)); }

    /*
     * Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne depompiers
     * URK OK
     */
    @GetMapping(value = "/phoneAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> phoneAlert(@RequestParam int firestation_number)  {
        return builderResponse.CustomResponse(personService.phoneAlert(fireStationService.findById(firestation_number))); }


}
