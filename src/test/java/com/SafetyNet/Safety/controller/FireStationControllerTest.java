package com.SafetyNet.Safety.controller;

import com.SafetyNet.Safety.model.FireStation;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService mockFireStationService;
    @MockBean
    private PersonService mockPersonService;

    private String messageTrue = "{\"Message\":\"L'operation a ete realise avec succes\"}";
    private String messageFalse = "{\"Message\":\"L'operation n'a pas ete realise\"}";
    private String firestation = "{\n" +
            "        \"station\": 5,\n" +
            "        \"address\": [\n" +
            "            \"1509 Culver St\",\n" +
            "            \"834 Binoc Ave\",\n" +
            "            \"748 Townings Dr\",\n" +
            "            \"112 Steppes Pl\"\n" +
            "        ]\n" +
            "}";


    @Test
    @DisplayName("Controller Firestation post True")
    void testFirestationPostTrue() throws Exception {
        // Setup
        when(mockFireStationService.save(any(FireStation.class))).thenReturn(true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/firestation")
                        .content(firestation).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(messageTrue, response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Firestation post False")
    void testFirestationPostFalse() throws Exception {
        // Setup
        when(mockFireStationService.save(any(FireStation.class))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/firestation")
                        .content(firestation).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(messageFalse, response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Firestation Put True")
    void testFirestationPutTrue() throws Exception {
        // Setup
        when(mockFireStationService.update(any(FireStation.class), eq(0))).thenReturn(true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/firestation/{id}", 0)
                        .content(firestation).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(messageTrue, response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Firestation Put False")
    void testFirestationPutFalse() throws Exception {
        // Setup
        when(mockFireStationService.update(any(FireStation.class), eq(0))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/firestation/{id}", 0)
                        .content(firestation).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(messageFalse, response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Firestation Delete True")
    void testFirestationDeleteTrue() throws Exception {
        // Setup
        when(mockFireStationService.remove(any(FireStation.class))).thenReturn(true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/firestation")
                        .content(firestation).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(messageTrue, response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Firestation Delete False")
    void testFirestationDeleteFalse() throws Exception {
        // Setup
        when(mockFireStationService.remove(any(FireStation.class))).thenReturn(false);

        // Run the test

        MockHttpServletResponse response = mockMvc.perform(delete("/firestation")
                        .content(firestation).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        assertEquals(messageFalse, response.getContentAsString());
    }

    @Test
    @DisplayName("Controller Firestation Get")
    void testFirestationGet() throws Exception {
        // Setup
        when(mockPersonService.personByFirestation(any(FireStation.class))).thenReturn("result");

        // Configure FireStationService.findById(...).
        final FireStation fireStation = new FireStation(Arrays.asList("value"), 0);
        when(mockFireStationService.findById(0)).thenReturn(fireStation);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/firestation")
                        .param("stationNumber", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("result", response.getContentAsString());
    }

    @Test
    @DisplayName("Controller URLS fire")
    void testFire() throws Exception {
        // Setup
        when(mockPersonService.fire("address")).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
                        .param("address", "address")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("result", response.getContentAsString());
    }

    @Test
    @DisplayName("Controller URLS flood")
    void testFlood() throws Exception {
        // Setup
        when(mockPersonService.flood(Arrays.asList(0))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/flood/stations")
                        .param("station_number", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("result", response.getContentAsString());
    }
}
