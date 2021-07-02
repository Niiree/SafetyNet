package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.service.PersonService;
import com.SafetyNet.Safety.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;



    @PostMapping(value = "/person")
    public void personPost(@RequestBody Person person){
        personService.personSave(person);
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public void personUpdate(@PathVariable String firstName,String lastName,@RequestBody Person person){
        personService.personUpdate(firstName,lastName,person);
        //TODO Boolean réponse à rajouter partout
    }



    /*
    Suppresion d'un utilisateur en fonction de son firstname et lastname
    @Param Nom et prénom
     */
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public void personDelete(@PathVariable String firstName,@PathVariable String lastName){
        personService.personDelete(firstName,lastName);
    }

    /*
 Récuperation d'une liste de Person
  */
    @GetMapping(value = "/personList")
    public List<Person> listePersons(){ return personService.findAll(); }



    ///////////////////////////////
    /////          URLS       /////
    ///////////////////////////////

    /*
     * URl doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
     * TODO A FINIR
     */
    @GetMapping(value = "/childAlert")
    public List<Person> childAlert(@RequestParam String address){

        return null;
    }

    /*
     * URl doit retourner les adresses mail de tous les habitants de la ville
     * TODO A FINIR
     */
    @GetMapping(value = "/personInfo/Name/{lastName}")
    public List<Person> personInfoName(@PathVariable String lastName){
        return personService.personByName(lastName);
    }

    /*
     * URl doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doiventtoutes apparaître
     * URL OK
     */
    @GetMapping (value = "/communityEmail")
    public List<String> communityEmail(@RequestParam String city){
    return personService.emailByCity(city);
    }




}
