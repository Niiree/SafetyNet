package com.SafetyNet.Safety.controller;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.service.FireStationService;
import com.SafetyNet.Safety.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService mockPersonService;
    @MockBean
    private FireStationService mockFireStationService;

    private String messageTrue = "{\"Message\":\"L'operation a ete realise avec succes\"}";
    private String messageFalse = "{\"Message\":\"L'operation n'a pas ete realise\"}";
    private String person = "{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"address\":\"15109 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\",\"birthdate\":\"03/06/1984\",\"allergies\":[\"nillacilan\"],\"medical\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"adult\":true}";

    @Test
    @DisplayName("Controller Post Person")
    void testPersonPost() throws Exception {
        // Setup
        when(mockPersonService.personSave(any(Person.class))).thenReturn(true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/person")
                        .content(person).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(messageTrue, response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Post Person false")
    void testPersonPostFalse() throws Exception {
        // Setup
        when(mockPersonService.personSave(any(Person.class))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/person")
                        .content(person).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(messageFalse, response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Update Person")
    void testPersonUpdate() throws Exception {
        // Setup
        when(mockPersonService.personUpdate(any(Person.class))).thenReturn(true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/person")
                        .content(person).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(messageTrue, response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Update Person false")
    void testPersonUpdateFalse() throws Exception {
        // Setup
        when(mockPersonService.personUpdate(any(Person.class))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/person")
                        .content(person).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(messageFalse, response.getContentAsString());
    }


    @Test
    @DisplayName("Controller Delete Person")
    void testPersonDelete() throws Exception {
        // Setup
        when(mockPersonService.personDelete("firstName", "lastName")).thenReturn(true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/person/{firstName}/{lastName}", "firstName", "lastName")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(messageTrue, response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Delete Person false")
    void testPersonDeleteFalse() throws Exception {
        // Setup
        when(mockPersonService.personDelete("firstName", "lastName")).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/person/{firstName}/{lastName}", "firstName", "lastName")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(messageFalse, response.getContentAsString());
    }


    @Test
    @DisplayName("Controller URL ChildAlert")
    void testChildAlert() throws Exception {
        // Setup
        when(mockPersonService.childAlert("address")).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/childAlert")
                        .param("address", "address")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("result", response.getContentAsString());
    }

    @Test
    @DisplayName("Controller URL PersonInfoName")
    void testPersonInfoName() throws Exception {
        // Setup
        when(mockPersonService.personByName("firstName", "lastName")).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/personInfo")
                        .param("firstName", "firstName")
                        .param("lastName", "lastName")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("result", response.getContentAsString());
    }

    @Test
    @DisplayName("Controller URL CommunityEmail")
    void testCommunityEmail() throws Exception {
        // Setup
        when(mockPersonService.communityEmail("city")).thenReturn(Arrays.asList("value"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/communityEmail")
                        .param("city", "city")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[\"value\"]", response.getContentAsString());
    }

    @Test
    @DisplayName("Controller URL CommunityEmail False")
    void testCommunityEmail_PersonServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockPersonService.communityEmail("city")).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/communityEmail")
                        .param("city", "city")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    @DisplayName("Controller URL phoneAlert")
    void testPhoneAlert() throws Exception {
        // Setup
        when(mockPersonService.phoneAlert(any(FireStation.class))).thenReturn("result");

        // Configure FireStationService.findById(...).
        final FireStation fireStation = new FireStation(Arrays.asList("value"), 0);
        when(mockFireStationService.findById(0)).thenReturn(fireStation);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/phoneAlert")
                        .param("firestation_number", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("result", response.getContentAsString());
    }

}
