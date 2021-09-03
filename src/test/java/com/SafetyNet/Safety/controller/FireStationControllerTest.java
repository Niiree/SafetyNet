package com.SafetyNet.Safety.controller;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.service.FireStationService;
import com.SafetyNet.Safety.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void testFirestationPost() throws Exception {
        // Setup
        when(mockFireStationService.save(any(FireStation.class))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/firestation")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testFirestationPut() throws Exception {
        // Setup
        when(mockFireStationService.update(any(FireStation.class), eq(0))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/firestation/{id}", 0)
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testFirestationPut_ThrowsJsonProcessingException() throws Exception {
        // Setup
        when(mockFireStationService.update(any(FireStation.class), eq(0))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/firestation/{id}", 0)
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testFirestationDelete() throws Exception {
        // Setup
        when(mockFireStationService.remove(any(FireStation.class))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/firestation")
                .content("content").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testListeFireStation() throws Exception {
        // Setup

        // Configure FireStationService.findAll(...).
        final List<FireStation> fireStations = Arrays.asList(new FireStation(Arrays.asList("value"), 0));
        when(mockFireStationService.findAll()).thenReturn(fireStations);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/firestationAll")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testListeFireStation_FireStationServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockFireStationService.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/firestationAll")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testFirestation() throws Exception {
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
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testFirestation_ThrowsJsonProcessingException() throws Exception {
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
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testFire() throws Exception {
        // Setup
        when(mockPersonService.fire("address")).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
                .param("address", "address")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testFire_PersonServiceThrowsJsonProcessingException() throws Exception {
        // Setup
        when(mockPersonService.fire("address")).thenThrow(JsonProcessingException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/fire")
                .param("address", "address")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testFlood() throws Exception {
        // Setup
        when(mockPersonService.flood(Arrays.asList(0))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/flood/stations")
                .param("station_number", "0")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }

    @Test
    void testFlood_ThrowsJsonProcessingException() throws Exception {
        // Setup
        when(mockPersonService.flood(Arrays.asList(0))).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/flood/stations")
                .param("station_number", "0")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("result");
    }
}
