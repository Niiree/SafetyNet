package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.service.FireStationService;
import com.SafetyNet.Safety.service.PersonService;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.util.BuilderResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PersonController {
    private final static Logger logger = LogManager.getLogger("PersonController") ;
    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationService fireStationService;

    private BuilderResponse builderResponse = new BuilderResponse();


    @PostMapping(value = "/person",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> personPost(@RequestBody Person person) {
        logger.info("Post /person"+person);
        return builderResponse.responseBoolean(personService.personSave(person)); }

    @PutMapping(value = "/person")
    public ResponseEntity<?> personUpdate(@RequestBody Person person) {
        logger.info("Put /person ");
       return builderResponse.responseBoolean(personService.personUpdate(person)); }

    /*
    Suppresion d'un utilisateur en fonction de son firstname et lastname
    @Param Nom et prénom
     */
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<?> personDelete(@PathVariable String firstName, @PathVariable String lastName) {
        logger.info("Delete /person with param ");
    return builderResponse.responseBoolean(personService.personDelete(firstName,lastName)); }

    ///////////////////////////////
    /////          URLS       /////
    ///////////////////////////////

    /*
     * URl doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     * URL OK
     */
    @GetMapping(value = "/childAlert",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> childAlert(@RequestParam String address)  {
        logger.info("Get /childAlert with param " + address);
        return builderResponse.customResponse(personService.childAlert(address)); }

    /*
     * URl doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doiventtoutes apparaître
     * URL OK
     */
    @GetMapping(value = "/personInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> personInfoName(@RequestParam String firstName, String lastName) {
        logger.info("Get /personInfo with param first name " + firstName + " lastname "+ lastName);
        return builderResponse.customResponse(personService.personByName(firstName,lastName));
    }
    /*
     * URl doit retourner les adresses mail de tous les habitants de la ville
     *
     */
    @GetMapping(value = "/communityEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> communityEmail(@RequestParam String city) {
        logger.info("Get /communityEmail with param " + city);
       return builderResponse.customResponse(personService.communityEmail(city)); }

    /*
     * Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne depompiers
     * URK OK
     */
    @GetMapping(value = "/phoneAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> phoneAlert(@RequestParam int firestation_number)  {
        logger.info("Get Phone Alert with param "+firestation_number);
        return builderResponse.customResponse(personService.phoneAlert(fireStationService.findById(firestation_number))); }


}
