package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.util.JacksonConfiguration;
import com.SafetyNet.Safety.util.exceptions.PersonIntrouvableException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Import(JacksonConfiguration.class)
class PersonServiceTest {

    @MockBean
    private FireStationService firestationServiceUnderTest;

    @MockBean
    private PersonService personServiceUnderTest;

    private FireStation firestation = new FireStation(Arrays.asList("Address1"),0);




    @BeforeEach
    void setUp()   {

        personServiceUnderTest = new PersonService();
        Person person = new Person("firstName", "lastName", "Address1", "cityTest", "zip", "0606060606", "email@gmail.com");
        Person child = new Person("User3", "User3", "Address1", "city", "zip", "0606060655", "test@gmail.com");

        child.setBirthdate("21/09/2021");
        person.setBirthdate("01/01/2000");
        List<Person> personList = new ArrayList<>();
        personServiceUnderTest.personSave(person);
        personServiceUnderTest.personSave(child);
        personList.add(person);
        personList.add(child);

        //firestationServiceUnderTest = Mock(FireStationService.class);

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
    void testPersonUpdate() {
        // Setup
         Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");

        // Run the test
         boolean result = personServiceUnderTest.personUpdate(person);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testFindAll() {
        // Setup
         Person person2 = new Person("user1", "user2", "address", "city", "zip", "phone", "email");
         personServiceUnderTest.personSave(person2);

        // Run the test
        List<Person> result = personServiceUnderTest.findAll();

        // Verify the results
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void testFindByFirstNameLastName() {

        // Run the test
         Person result = personServiceUnderTest.findByFirstNameLastName("firstName", "lastName");

        // Verify the result

        assertThat(result.getFirstName()).isEqualTo("firstName");
        assertThat(result.getLastName()).isEqualTo("lastName");

    }

    @Test
    void testEmailByCity() {
        // Setup

        // Run the test
         List<String> result = personServiceUnderTest.emailByCity("cityTest");

        // Verify the results
        assertThat(result).isEqualTo(Arrays.asList("email@gmail.com"));
    }

    @Test
    void testPersonByName() {
        //TODO DATE BIRTHDATE

        // Run the test
         String result = personServiceUnderTest.personByName("firstName", "lastName");
      //  String var ='{\"firstName":"Nicolas","lastName":"Le stunff","address":"1509 Culver St","city":"Culver","zip":"97451","phone":"841-874-6512","email":"jaboyd@email.com"}';

        assertThat(result).isEqualTo("{\"lastName\":\"lastName\",\"address\":\"Address1\",\"email\":\"email@gmail.com\",\"birthdate\":\"01/01/2000\"}");

    }

    @Test
    void testChildAlert() {

        // Run the test
         String result = personServiceUnderTest.childAlert("Address1");

        // Verify the results
        assertThat(result).isEqualTo("{\"Person\":[{\"address\":\"Address1\",\"city\":\"city\",\"zip\":\"zip\",\"email\":\"test@gmail.com\",\"birthdate\":\"21/09/2021\",\"adult\":false}]}");

    }

    @Test
    void testPersonByFirestation() {
        // Setup
         FireStation firestation = new FireStation(Arrays.asList("Address1"), 0);

        // Run the test
         String result = personServiceUnderTest.personByFirestation(firestation);

        // Verify the results
        assertThat(result).isEqualTo("{\"Person\":[{\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"address\":\"Address1\",\"phone\":\"0606060606\"},{\"firstName\":\"User3\",\"lastName\":\"User3\",\"address\":\"Address1\",\"phone\":\"0606060655\"}],\"adulte\":1,\"enfants\":1}");
    }

    @Test
    void testPhoneAlert() throws Exception {
        // Run the test

         String resultat = personServiceUnderTest.phoneAlert(firestation);


        // Verify the results
        assertThat(resultat).isEqualTo("{\"Phone\":[\"0606060606\",\"0606060655\"]}");

    }


    @Test
    void testPhoneAlert_ThrowsJsonProcessingException() {
        // Setup
         FireStation firestation = new FireStation(Arrays.asList("value"), 0);

        // Run the test
        assertThatThrownBy(() -> personServiceUnderTest.phoneAlert(firestation)).isInstanceOf(PersonIntrouvableException.class);
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
        assertThat(result).isEqualTo("result");
    }

    //  @ExtendWith(MockitoExtension.class)
 //   @MockitoSettings(strictness = Strictness.LENIENT)
 //   @Test
    void testFire_FireStationServiceReturnsNoItems()  {
        // Setup
        when(firestationServiceUnderTest.findByAddress("address")).thenReturn(Collections.emptyList());
        // Run the test
         String result = personServiceUnderTest.fire("address");

        // Verify the results
     //   assertThat(result).isEqualTo("result");
       assertThat(result).isEqualTo(null);
    }


    //@Test
    void testFlood() {
     //    FireStation fireStation = new FireStation(Arrays.asList("value"), 0);
        FireStation fireStation = new FireStation();
        when(firestationServiceUnderTest.findById(0)).thenReturn(fireStation);

        // Run the test
         String result = personServiceUnderTest.flood(Arrays.asList(0));

        // Verify the results
        assertThat(result).isEqualTo("{\"Firestation0\":{\"station\":0},\"Utilisateur0\":[]}");
    }
}
