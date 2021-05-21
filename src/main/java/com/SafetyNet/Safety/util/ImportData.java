package com.SafetyNet.Safety.util;

import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.repository.PersonRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class ImportData {

    private PersonRepository PersonDao;

    public void load() throws IOException {
        InputStream url = new URL("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json").openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(url, Charset.forName("UTF-8")));
        StringBuilder sb = new StringBuilder();
        String cp;

        while ((cp = rd.readLine()) != null) {
            System.out.println(cp);
            sb.append(cp);
        }
        JsonObject jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();

        JsonElement persons = jsonObject.get("persons");
        JsonElement firestations = jsonObject.get("firestations");
        JsonElement medicalrecords = jsonObject.get("medicalrecords");

        com.google.gson.JsonArray personnsArray = persons.getAsJsonArray();
        for (JsonElement per:personnsArray) {
            Gson gson = new Gson();
            Person person = gson.fromJson(per,Person.class);
            PersonDao.PersonSave(person);
        }
    }
}
