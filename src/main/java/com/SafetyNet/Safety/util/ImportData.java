package com.SafetyNet.Safety.util;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.service.FireStationService;
import com.SafetyNet.Safety.service.PersonService;
import com.google.gson.*;
import com.SafetyNet.Safety.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;

@Service
public class ImportData {

    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationService fireStationService;

    private Gson gson = new Gson();


    @PostConstruct
    public void load() throws IOException {
        InputStream url = new URL("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json").openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(url, Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();
        String cp;

        while ((cp = rd.readLine()) != null) {
            sb.append(cp);
        }
        JsonObject jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();

        JsonElement persons = jsonObject.get("persons");
        JsonElement firestations = jsonObject.get("firestations");
        JsonElement medicalrecords = jsonObject.get("medicalrecords");

        loadPersons(persons);
        loadFireStations(firestations);
        loadMedicalRecords(medicalrecords);

    }

    private void loadPersons(JsonElement persons){
        JsonArray personsArray = persons.getAsJsonArray();
        for (JsonElement per:personsArray
            ) {
            Person person = gson.fromJson(per,Person.class);
            personService.personSave(person);
        }
    }

    
    private void loadFireStations(JsonElement fireStations){
        JsonArray firestationArray = fireStations.getAsJsonArray();

        for (JsonElement jsonfirestation:firestationArray
            ) {

            JsonObject jsonObject = jsonfirestation.getAsJsonObject();
            int id = jsonObject.get("station").getAsInt();
            String addresse = jsonObject.get("address").getAsString();
            FireStation fireStation = new FireStation();
            fireStation.setAddress(addresse);
            fireStation.setIdStation(id);

            if(fireStationService.findById(fireStation.getIdStation())!= null){
                fireStationService.update(fireStation,test);
            }else{
                fireStationService.save(fireStation);
            }
        }

    }

    /*
    * Chargement des medicalRecords dans chaque Person
    * */
    private void loadMedicalRecords(JsonElement medicalRecords) {
        JsonArray medicalRecordsArray = medicalRecords.getAsJsonArray();

        for (JsonElement medicalRecord : medicalRecordsArray
            ){
            JsonObject jsonObject = medicalRecord.getAsJsonObject();
            String firstName = jsonObject.get("firstName").getAsString();
            String lastName = jsonObject.get("lastName").getAsString();

            Person person = personService.findByFirstNameLastName(firstName, lastName);

            person.setMedical(gson.fromJson(jsonObject.getAsJsonArray("medications"), List.class));
            person.setAllergies(gson.fromJson(jsonObject.getAsJsonArray("allergies"), List.class));

            String dateString =jsonObject.get("birthdate").getAsString();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            try {
                person.setBirthdate(formatter.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
