package com.SafetyNet.Safety.controller;

import com.SafetyNet.Safety.model.Person;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MedicalRecordsController.class)
public class MedicalRecordsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService mockPersonSerivce;

    private String messageTrue = "{\"Message\":\"L'operation a ete realise avec succes\"}";
    private String messageFalse = "{\"Message\":\"L'operation n'a pas ete realise\"}";
    private String person = "{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"address\":\"15109 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\",\"birthdate\":\"03/06/1984\",\"allergies\":[\"nillacilan\"],\"medical\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"adult\":true}";


    @Test
    @DisplayName("Controller Post medicalRecords ")
    void testMedicalRecordPost() throws Exception {
        //setup
        when(mockPersonSerivce.personMedicalPost(any(Person.class))).thenReturn(true);
        //run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/medicalRecord")
                        .content(person).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(messageTrue,response.getContentAsString());

    }

    @Test
    @DisplayName("Controller Post medicalRecords false")
    void testMedicalRecordPostFalse() throws Exception {
        //setup
        when(mockPersonSerivce.personMedicalPost(any(Person.class))).thenReturn(false);
        //run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/medicalRecord")
                        .content(person).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(messageFalse,response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Put medicalRecords True")
    void testMedicalRecordPut() throws Exception {
        //setup
        when(mockPersonSerivce.personMedicalPut(any(Person.class))).thenReturn(true);
        //run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/medicalRecord")
                        .content(person).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(messageTrue,response.getContentAsString());

    }

    @Test
    @DisplayName("Controller Put medicalRecords false")
    void testMedicalRecordPutFalse() throws Exception {
        //setup
        when(mockPersonSerivce.personMedicalPut(any(Person.class))).thenReturn(false);
        //run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/medicalRecord")
                        .content(person).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(messageFalse,response.getContentAsString());
    }


    @Test
    @DisplayName("Controller Delete medicalRecords true")
    void testMedicalRecordDelete() throws Exception {
        //setup
        when(mockPersonSerivce.personMedicalDelete("John","Boyd")).thenReturn(true);
        //run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/medicalRecord/{firstName}/{lastName}","John","Boyd")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(messageTrue,response.getContentAsString());

    }

    @Test
    @DisplayName("Controller Delete medicalRecords false")
    void testMedicalRecordDeleteFalse() throws Exception {
        //setup
        when(mockPersonSerivce.personMedicalDelete("John","Boyd")).thenReturn(false);
        //run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/medicalRecord/{firstName}/{lastName}","John","Boyd")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(messageFalse,response.getContentAsString());
    }





}
