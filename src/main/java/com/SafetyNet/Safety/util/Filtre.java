package com.SafetyNet.Safety.util;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;

public class Filtre {

    public JsonObject filtreListPerson(List<Person> personList, String... ListAllExcept) throws JsonProcessingException {
        SimpleBeanPropertyFilter filtreUrl = SimpleBeanPropertyFilter.serializeAllExcept(ListAllExcept);
        FilterProvider list = new SimpleFilterProvider().addFilter("Filtre", filtreUrl);
        MappingJacksonValue personsFiltre = new MappingJacksonValue(personList);
        personsFiltre.setFilters(list);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(list);

        String jsonData = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(personsFiltre);

        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        return jsonObject;

    }

    public JsonObject filtreListFirestation(List<FireStation> fireStationList, String... ListAllExcept) throws JsonProcessingException {

        SimpleBeanPropertyFilter filtreUrl = SimpleBeanPropertyFilter.serializeAllExcept(ListAllExcept);
        FilterProvider list = new SimpleFilterProvider().addFilter("FiltreFire", filtreUrl);
        MappingJacksonValue firestationsFiltre = new MappingJacksonValue(fireStationList);
        firestationsFiltre.setFilters(list);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(list);

        String jsonData = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(firestationsFiltre);

        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        return jsonObject;

    }

    public JsonObject filtreFirestation(FireStation fireStationList, String... ListAllExcept) throws JsonProcessingException {

        SimpleBeanPropertyFilter filtreUrl = SimpleBeanPropertyFilter.serializeAllExcept(ListAllExcept);
        FilterProvider list = new SimpleFilterProvider().addFilter("FiltreFire", filtreUrl);
        MappingJacksonValue firestationsFiltre = new MappingJacksonValue(fireStationList);
        firestationsFiltre.setFilters(list);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(list);

        String jsonData = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(firestationsFiltre);

        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        return jsonObject;

    }


}
