package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonServiceTest {

    private static PersonService personService;


    @BeforeAll
    private void setUp(){
        PersonService personService = new PersonService();
    }

    @BeforeEach
    private void setUpPerTest(){
        Person person = new Person("Utilisateur1", "LastName", "address 1 ", "city1", "zip1", "phone1", "email1");
        personService.personSave(person);

    }

    @Test
    @DisplayName("Enregistrement d'une nouvelle person")
    public void PersonSave(){
    }

    @Test
    @DisplayName("Suppression d'une person")
    public void PersonDelete(){

    }

}
