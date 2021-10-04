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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import java.util.*;
import java.util.logging.Logger;

@Service
public class ImportData {

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    private Gson gson = new Gson();
    private URL url  = new URL("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json");

    public ImportData() throws MalformedURLException {
    }

    @PostConstruct
    public Boolean load() {
        //Récuperation du fichier Json et extraction des données.
        JsonObject jsonObject = loadFile();
        if (jsonObject == null){
            return false;
        }
        if(jsonObject.isJsonNull()) {
            return false;

        }else{
             JsonElement persons = jsonObject.get("persons");
             JsonElement firestations = jsonObject.get("firestations");
             JsonElement medicalrecords = jsonObject.get("medicalrecords");
            //Traitement des differentes informations
            loadPersons(persons);
            loadFireStations(firestations);
            loadMedicalRecords(medicalrecords);
            return true;

        }

    }

    public JsonObject loadFile()   {

       InputStream urlLoad = null;
        JsonObject jsonObject = null;
        try {
            urlLoad = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(urlLoad, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            String cp;
            while ((cp = rd.readLine()) != null) {
                sb.append(cp);
            }
            jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();
        }catch (Exception e){
            Logger.getLogger(e.getMessage());
        }finally {
            if(urlLoad != null){
                try{
                    urlLoad.close();
                }catch (Exception e){
                    Logger.getLogger(e.getMessage());
                }
            }
        }
        return jsonObject;
    }


    /*
  Chargement des Persons
  */
    private void loadPersons(JsonElement persons) {
        JsonArray personsArray = persons.getAsJsonArray();
        for (JsonElement jsonPerson : personsArray
        ) {
            Person person = gson.fromJson(jsonPerson, Person.class);
            personService.personSave(person);
        }
    }

    /*
     Chargement des FireStations
     */
    private void loadFireStations(JsonElement fireStations) {
        JsonArray firestationArray = fireStations.getAsJsonArray();
        for (JsonElement jsonFireStation : firestationArray
        ) {
            JsonObject jsonObject = jsonFireStation.getAsJsonObject();

            int id = jsonObject.get("station").getAsInt();
            String address = jsonObject.get("address").getAsString();

            // Si l'ID existe, alors on ajoute l'adresse, sinon on sauvegarde.
            if (fireStationService.findById(id) != null) {
                //Vérification doublon address
                if (!fireStationService.findById(id).getAddress().contains(address)) {
                    fireStationService.addAddress(address, id);
                }
            } else {
                FireStation fireStation = new FireStation(new ArrayList<String>(Arrays.asList(new String[]{address})), id);
                fireStationService.save(fireStation);
            }
        }
    }

    /*
     Chargement des medicalRecords dans chaque Person
     */
    private void loadMedicalRecords(JsonElement medicalRecords)  {
        JsonArray medicalRecordsArray = medicalRecords.getAsJsonArray();

        for (JsonElement jsonMedicalRecord : medicalRecordsArray
        ) {
            JsonObject jsonObject = jsonMedicalRecord.getAsJsonObject();
            String firstName = jsonObject.get("firstName").getAsString();
            String lastName = jsonObject.get("lastName").getAsString();

            Person person = personService.findByFirstNameLastName(firstName, lastName);

            person.setMedical(gson.fromJson(jsonObject.getAsJsonArray("medications"), List.class));
            person.setAllergies(gson.fromJson(jsonObject.getAsJsonArray("allergies"), List.class));

            String dateString = jsonObject.get("birthdate").getAsString();
            person.setBirthdate(dateString);

        }
    }
}
