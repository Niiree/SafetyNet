package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {

    private static PersonService personService;
    private Person person;


    @BeforeAll
    private static void setUp() {

    }

    @BeforeEach
    private void setUpPerTest() {
        PersonService personService = new PersonService();
        Person person = new Person("Utilisateur1", "LastName", "address 1 ", "city1", "zip1", "phone1", "email1");


    }

    @Test
    @DisplayName("Enregistrement d'une nouvelle person")
    public void PersonSave() {
        person = new Person("Utilisateur2", "LastName", "address 1 ", "city1", "zip1", "phone1", "email1");
        personService.personSave(person);
        assertEquals(personService.findByFirstNameLastName("Utilisateur2", "LastName"), person);

    }

    @Test
    @DisplayName("Suppression d'une person")
    public void PersonDelete() {

    }

}
