package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.util.JacksonConfiguration;
import com.SafetyNet.Safety.util.exceptions.PersonIntrouvableException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Import(JacksonConfiguration.class)
class PersonServiceTest {

    @Mock
    private FireStationService mockFireStationService;

    @Mock
    private PersonService personServiceUnderTest;

    private FireStation firestation = new FireStation(Arrays.asList("Address1"),0);



//    @BeforeAll
//    void setUp(){
//        final Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
//        List<Person> personList = new ArrayList<>();
//        personList.add(person);
//        when(personServiceUnderTest.findAll()).thenReturn(personList);
//    }

    @Test
    void testPersonSave() {
        // Setup
        final Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");

        // Run the test
        personServiceUnderTest.personSave(person);

        // Verify the results
    }

    @Test
    void testPersonDelete() {
        // Setup

        // Run the test
        final boolean result = personServiceUnderTest.personDelete("firstName", "lastName");

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testPersonUpdate() {
        // Setup
        final Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");

        // Run the test
        final boolean result = personServiceUnderTest.personUpdate(person);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testFindAll() {
        // Setup
        final Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(personServiceUnderTest.findAll()).thenReturn(personList);
        // Run the test
        final List<Person> result = personServiceUnderTest.findAll();

        // Verify the results
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testFindByFirstNameLastName() {
        // Setup

        // Run the test
        final Person result = personServiceUnderTest.findByFirstNameLastName("firstName", "lastName");

        // Verify the results
    }

    @Test
    void testEmailByCity() {
        // Setup

        // Run the test
        final List<String> result = personServiceUnderTest.emailByCity("city");

        // Verify the results
        assertThat(result).isEqualTo(Arrays.asList("value"));
    }

    @Test
    void testPersonByName() {
        // Setup
        final Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(personServiceUnderTest.findAll()).thenReturn(personList);
        // Run the test
        final String result = personServiceUnderTest.personByName("firstName", "lastName");
      //  String var ='{\"firstName":"Nicolas","lastName":"Le stunff","address":"1509 Culver St","city":"Culver","zip":"97451","phone":"841-874-6512","email":"jaboyd@email.com"}';

       //TODO
        // Gson gson = new Gson();
        //String json = gson.toJson(user);


        // Verify the results
    //    assertThat(result).isEqualTo(var);
    }

    @Test
    void testChildAlert() {
        // Setup

        // Run the test
        final String result = personServiceUnderTest.childAlert("address");

        // Verify the results
        assertThat(result).isEqualTo("result");
    }

    @Test
    void testPersonByFirestation() {
        // Setup
        final FireStation firestation = new FireStation(Arrays.asList("value"), 0);

        // Run the test
        final String result = personServiceUnderTest.personByFirestation(firestation);

        // Verify the results
        assertThat(result).isEqualTo("result");
    }

    @Test
    void testPhoneAlert() throws Exception {
        // Setup
        final FireStation firestation = new FireStation(Arrays.asList("value"), 0);

        // Run the test
        final String result = personServiceUnderTest.phoneAlert(firestation);

        // Verify the results
        assertThat(result).isEqualTo("result");
    }

    @Test
    void testPhoneAlert_ThrowsJsonProcessingException() {
        // Setup
        final FireStation firestation = new FireStation(Arrays.asList("value"), 0);

        // Run the test
        assertThatThrownBy(() -> personServiceUnderTest.phoneAlert(firestation)).isInstanceOf(JsonProcessingException.class);
    }

    @Test
    void testCommunityEmail() {
        // Setup
        Person mock = mock(Person.class);

        // Run the test
        final List<String> result = personServiceUnderTest.communityEmail("city");

        // Verify the results
        assertThat(result).isEqualTo(Arrays.asList("value"));
    }

    @Test
    void testFire() throws Exception {
        // Setup
        //TODO mock le stream?

        Stream<List<Person>> mockStream = mock(Stream.class);

        // Configure FireStationService.findByAddress(...).
        final List<FireStation> fireStations = Arrays.asList(new FireStation(Arrays.asList("value"), 0));
        final Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");

        List<Person> personList = new ArrayList<>();
        personList.add(person);
  //      when(personList.stream()).thenReturn((Stream<Person>) person);
        when(mockFireStationService.findByAddress("address")).thenReturn(fireStations);


        // Run the test
        final String result = personServiceUnderTest.fire("address");

        // Verify the results
        assertThat(result).isEqualTo("result");
    }

    @ExtendWith(MockitoExtension.class)
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testFire_FireStationServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockFireStationService.findByAddress("address")).thenReturn(Collections.emptyList());

        // Run the test
        final String result = personServiceUnderTest.fire("address");

        // Verify the results
     //   assertThat(result).isEqualTo("result");
       assertThat(result).isEqualTo(null);
    }


    @Test
    void testFlood() {
        final FireStation fireStation = new FireStation(Arrays.asList("value"), 0);
        when(mockFireStationService.findById(0)).thenReturn(fireStation);

        // Run the test
        final String result = personServiceUnderTest.flood(Arrays.asList(0));

        // Verify the results
        assertThat(result).isEqualTo("{\"Firestation0\":{\"station\":0},\"Utilisateur0\":[]}");
    }
}
