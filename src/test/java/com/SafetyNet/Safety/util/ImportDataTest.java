package com.SafetyNet.Safety.util;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.service.FireStationService;
import com.SafetyNet.Safety.service.PersonService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ImportDataTest {



    @Mock
    private PersonService mockPersonService;

    @Mock
    private FireStationService mockFireStationService;

    @Spy
    @InjectMocks
    private ImportData mockImport;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoad()   {

    //    ImportData mockImport = Mockito.mock(ImportData.class);
       // PersonService mockPersonService = Mockito.mock(PersonService.class);

   // Setup
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("persons","{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }");
        jsonObject.addProperty("firestations","{ \"address\":\"1509 Culver St\", \"station\":\"3\" }");
        jsonObject.addProperty("medicalrecords","{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }");

        when(mockImport.loadFile()).thenReturn(jsonObject);
        when(mockPersonService.personSave(any(Person.class))).thenReturn(true);
        when(mockFireStationService.save(any(FireStation.class))).thenReturn(true);
        when(mockPersonService.findByFirstNameLastName("John","Boyd")).thenReturn(new Person());
        // Run the test
        mockImport.load();

        // Verify the results
//        verify(mockFireStationService).addAddress("address", 0);
       // verify(mockFireStationService).save(any(FireStation.class));
    }

    @Test
    void testLoad_ThrowsIOException() {
        // Setup

    }
}
