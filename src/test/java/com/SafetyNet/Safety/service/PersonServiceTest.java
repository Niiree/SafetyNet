package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PersonServiceTest {


    @InjectMocks
    private PersonService personServiceUnderTest;
    @Mock
    private FireStationService firestationServiceUnderTest;

    private FireStation firestation = new FireStation(Arrays.asList("Address1"),0);


    @BeforeEach
    void setUp()   {
        MockitoAnnotations.openMocks(this);
        Person person = new Person("firstName", "lastName", "Address1", "cityTest", "zip", "0606060606", "email@gmail.com");
        Person medicalPerson = new Person("User2", "lastName2", "Address1", "city", "zip", "0606060655", "test@gmail.com");
        Person child = new Person("User3", "User3", "Address9", "city9", "zip", "0606060655", "test@gmail.com");

        child.setBirthdate("21/09/2021");
        child.setAllergies(Arrays.asList("nuts"));
        child.setMedical(Arrays.asList("none"));
        person.setBirthdate("01/01/2000");
        List<Person> personList = new ArrayList<>();
        personServiceUnderTest.personSave(person);
        personServiceUnderTest.personSave(child);
        personServiceUnderTest.personSave(medicalPerson);
        personList.add(person);
        personList.add(child);

    }

    @Test
    @DisplayName("Sauvegarde Person")
    void testPersonSave() {
        // Setup
        Person person = new Person("User", "Test", "address", "city", "zip", "phone", "email");

        // Run the test
        boolean result = personServiceUnderTest.personSave(person);

        Person person1 = personServiceUnderTest.findByFirstNameLastName("User","Test");

        // Verify the results
        assertThat(result).isTrue();
        assertThat(person1).isEqualTo(person);
    }

    @Test
    @DisplayName("Sauvegarde Person null")
    void testPersonSavenull() {
        // Setup
        Person person = null;
        // Run the test
        boolean result = personServiceUnderTest.personSave(person);

        // Verify the results
        assertThat(result).isFalse();


    }

    @Test
    @DisplayName("Suppresion Person")
    void testPersonDelete() {
        // Setup

        // Run the test
        boolean result = personServiceUnderTest.personDelete("firstName", "lastName");
        Person person = personServiceUnderTest.findByFirstNameLastName("firstName","lastName");

        // Verify the results
        assertThat(result).isTrue();
        assertThat(person).isNull();
    }

    @Test
    @DisplayName("Suppresion Person non existante")
    void testPersonDeleteFalse() {
        // Run the test
        boolean result = personServiceUnderTest.personDelete("ok", "ok");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Update d'une person")
    void testPersonUpdate() {
        // Setup
         Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
         person.setBirthdate("24/06/1994");
         person.setAllergies(Arrays.asList("none"));
         person.setMedical(Arrays.asList("non"));

        // Run the test
         boolean result = personServiceUnderTest.personUpdate(person);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Update d'une person Null")
    void testPersonUpdateNull() {
        // Setup
        Person person = new Person("firstName", "lastName", null,null,null,null,null);
        person.setFirstName("firstname");
        person.setLastName("lastname");

        // Run the test
        boolean result = personServiceUnderTest.personUpdate(person);

        // Verify the results
        assertThat(result).isFalse();
    }
    @Test
    @DisplayName("Update d'une person not work")
    void testPersonUpdatNotWork() {
        // Setup
        Person person = new Person();
        person.setFirstName("firstnamek");
        person.setLastName("lastnamek");

        // Run the test
        boolean result = personServiceUnderTest.personUpdate(person);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Post d'un Medical")
    void testPersonMedicalPost(){

        // Setup
        Person person = new Person();
        person.setFirstName("User2");
        person.setLastName("lastName2");
        person.setBirthdate("24/06/1994");
        person.setMedical(Arrays.asList("none"));
        person.setAllergies(Arrays.asList("Nuts"));

        //run the test
        boolean result = personServiceUnderTest.personMedicalPost(person);
        Person resultPerson = personServiceUnderTest.findByFirstNameLastName("User2","lastName2");

        //Veerify the results
        assertThat(result).isTrue();
        assertThat(resultPerson.getMedical()).isEqualTo(Arrays.asList("none"));
        assertThat(resultPerson.getAllergies()).isEqualTo(Arrays.asList("Nuts"));
        assertThat(resultPerson.getBirthdate()).isEqualTo("24/06/1994");

    }

    @Test
    @DisplayName("Put d'un Medical")
    void testPersonMedicalPut(){

        // Setup
        Person person = new Person();
        person.setFirstName("User3");
        person.setLastName("User3");
        person.setBirthdate("24/06/1994");
        person.setMedical(Arrays.asList("none"));
        person.setAllergies(Arrays.asList("Nuts"));

        //run the test
        boolean result = personServiceUnderTest.personMedicalPut(person);
        Person resultPerson = personServiceUnderTest.findByFirstNameLastName("User3","User3");

        //Veerify the results
        assertThat(result).isTrue();
        assertThat(resultPerson.getMedical()).isEqualTo(Arrays.asList("none"));
        assertThat(resultPerson.getAllergies()).isEqualTo(Arrays.asList("Nuts"));
        assertThat(resultPerson.getBirthdate()).isEqualTo("24/06/1994");
    }

    @Test
    @DisplayName("Delete d'un Medical")
    void testPersonMedicalDelete(){
        //run the test
        boolean result = personServiceUnderTest.personMedicalDelete("User3","User3");
        Person resultPerson = personServiceUnderTest.findByFirstNameLastName("User3","User3");

        //Veerify the results
        assertThat(result).isTrue();
        assertThat(resultPerson.getMedical()).isEqualTo(null);
        assertThat(resultPerson.getAllergies()).isEqualTo(null);
        assertThat(resultPerson.getBirthdate()).isEqualTo(null);
    }


    @Test
    @DisplayName("Récuperation des persons")
    void testFindAll() {
        // Setup
         Person person2 = new Person("user1", "user2", "address", "city", "zip", "phone", "email");
         personServiceUnderTest.personSave(person2);

        // Run the test
        List<Person> result = personServiceUnderTest.findAll();

        // Verify the results
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Récuperation de person par firstName / lastName")
    void testFindByFirstNameLastName() {
        // Run the test
         Person result = personServiceUnderTest.findByFirstNameLastName("firstName", "lastName");

        // Verify the result
        assertThat(result.getFirstName()).isEqualTo("firstName");
        assertThat(result.getLastName()).isEqualTo("lastName");
    }

    @Test
    @DisplayName("Email par ville")
    void testEmailByCity() {
        // Run the test
         List<String> result = personServiceUnderTest.emailByCity("cityTest");

        // Verify the results
        assertThat(result).isEqualTo(Arrays.asList("email@gmail.com"));
    }

    @Test
    @DisplayName("Person par FirstName")
    void testPersonByName() {
        // Run the test
         String result = personServiceUnderTest.personByName("firstName", "lastName");
         //Verify the results
        assertThat(result).isEqualTo("{\"lastName\":\"lastName\",\"address\":\"Address1\",\"email\":\"email@gmail.com\",\"birthdate\":\"01/01/2000\"}");
    }

    @Test
    @DisplayName("Person par FirstName faux")
    void testPersonByNameWrong() {
        // Run the test
        String result = personServiceUnderTest.personByName("firstNameNull", "lastNameNull");
        //  String var ='{\"firstName":"Nicolas","lastName":"Le stunff","address":"1509 Culver St","city":"Culver","zip":"97451","phone":"841-874-6512","email":"jaboyd@email.com"}';
        assertThat(result).isEqualTo(null);
    }

    @Test
    @DisplayName("URL ChildAlert")
    void testChildAlert() {
        // Run the test
         String result = personServiceUnderTest.childAlert("Address9");

        // Verify the results
        assertThat(result).isEqualTo("{\"Person\":[{\"address\":\"Address9\",\"city\":\"city9\",\"zip\":\"zip\",\"email\":\"test@gmail.com\",\"birthdate\":\"21/09/2021\",\"allergies\":[\"nuts\"],\"medical\":[\"none\"],\"adult\":false}]}");
    }

    @Test
    @DisplayName("URL Person par Firestation")
    void testPersonByFirestation() {
        // Setup
         FireStation firestation = new FireStation(Arrays.asList("Address1"), 0);

        // Run the test
         String result = personServiceUnderTest.personByFirestation(firestation);

        // Verify the results
        assertThat(result).isEqualTo("{\"Person\":[{\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"address\":\"Address1\",\"phone\":\"0606060606\"},{\"firstName\":\"User2\",\"lastName\":\"lastName2\",\"address\":\"Address1\",\"phone\":\"0606060655\"}],\"adulte\":1,\"enfants\":0}");
    }

    @Test
    void testPhoneAlert() throws Exception {
        // Run the test
         String resultat = personServiceUnderTest.phoneAlert(firestation);

        // Verify the results
        assertThat(resultat).isEqualTo("{\"Phone\":[\"0606060606\",\"0606060655\"]}");
    }

    @Test
    void testCommunityEmail() {
        // Run the test
         List<String> result = personServiceUnderTest.communityEmail("city");
        // Verify the results
        assertThat(result).isEqualTo(Arrays.asList("test@gmail.com"));
    }

    @Test
    void testCommunityEmailNull(){
        List<String> result = personServiceUnderTest.communityEmail("Null");

        assertThat(result).isNull();
    }

    @Test
    void testFire()   {
        // Configure FireStationService.findByAddress(...).
         List<FireStation> fireStations = Arrays.asList(new FireStation(Arrays.asList("value"), 0));
         Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(firestationServiceUnderTest.findByAddress("Address1")).thenReturn(fireStations);
        // Run the test
         String result = personServiceUnderTest.fire("Address1");
        // Verify the results
        assertThat(result).isEqualTo("{\"Person\":[{\"firstName\":\"firstName\",\"address\":\"Address1\",\"phone\":\"0606060606\"},{\"firstName\":\"User2\",\"address\":\"Address1\",\"phone\":\"0606060655\"}],\"Firestation\":[{\"station\":0}]}");
    }

    @Test
    void testFlood() {

        FireStation fireStation = new FireStation();
        fireStation.setStation(0);
        fireStation.setAddress(Arrays.asList("Address1"));
        when(firestationServiceUnderTest.findById(0)).thenReturn(fireStation);

        // Run the test
        String result = personServiceUnderTest.flood(Arrays.asList(0));

        // Verify the results
        assertThat(result).isEqualTo("{\"Firestation0\":{\"station\":0},\"Utilisateur 0\":[{\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"phone\":\"0606060606\",\"birthdate\":\"01/01/2000\"},{\"firstName\":\"User2\",\"lastName\":\"lastName2\",\"phone\":\"0606060655\"}]}");
    }

    @Test
    void testFloodNull() {
        FireStation fireStation = new FireStation();
        fireStation.setStation(0);
        fireStation.setAddress(Arrays.asList("Address"));
        when(firestationServiceUnderTest.findById(0)).thenReturn(fireStation);

        // Run the test
         String result = personServiceUnderTest.flood(Arrays.asList(0));

        // Verify the results
        assertThat(result).isEqualTo(null);
    }
}
