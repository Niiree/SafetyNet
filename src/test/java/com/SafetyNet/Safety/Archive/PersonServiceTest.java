package com.SafetyNet.Safety.controller;

import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.service.PersonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PersonServiceTest {

    private static PersonService personService = new PersonService();
    private static Person person = new Person("Utilisateur1", "LastName", "address 1 ", "city1", "zip1", "phone1", "email1");



    @BeforeAll
    private static void setUp() {

    }

    @BeforeEach
    private void setUpPerTest() {
        System.out.println("beforeEach");
        PersonService personService = new PersonService();
        Person person = new Person("Utilisateur1", "LastName", "address 1 ", "city1", "zip1", "phone1", "email1");
        personService.personSave(person);

    }

    @Test
    @DisplayName("Enregistrement d'une nouvelle person")
    public void personSave() {
        person = new Person("Utilisateur2", "LastName", "address 1 ", "city1", "zip1", "phone1", "email1");
        personService.personSave(person);
        assertEquals(personService.findByFirstNameLastName("Utilisateur2", "LastName"), person);

    }

    @Test
    @DisplayName("Suppression d'une person")
    public void personDelete() {

        boolean reponse = personService.personDelete("Utilisateur1", "LastName");
        assertEquals(true,reponse);
        assertEquals(null,personService.findByFirstNameLastName("Utilisateur1","LastName"));


    }

    @Test
    @DisplayName("Mise à jour d'une person")
    public void personUpdate(){
        //TODO
    }

    @Test
    @DisplayName("findAll")
    public void findAll(){

    }

    @Test
    @DisplayName("findByFirstNameLastName")
    public void findByFirstNameLastName(){

    }

    @Test
    @DisplayName("findByFirstNameLastName")
    public void personByName(){}

    @Test
    @DisplayName("childAlert")
    public void childAlert(){}

    @Test
    @DisplayName("personByFirestation")
    public void personByFirestation(){}

    @Test
    @DisplayName("phoneAlert")
    public void phoneAlert(){}

    @Test
    @DisplayName("community")
    public void communityEmail(){}

    @Test
    @DisplayName("fire")
    public void fire(){}

    @Test
    @DisplayName("flood")
    public void flood(){}



}
