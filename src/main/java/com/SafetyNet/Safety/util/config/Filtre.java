package com.SafetyNet.Safety.util.config;

import com.SafetyNet.Safety.model.FireStation;
import com.SafetyNet.Safety.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.logging.log4j.LogManager;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;

public class Filtre {
    private final static org.apache.logging.log4j.Logger logger = LogManager.getLogger("Filtre") ;

    public JsonObject filtreAllExceptListPerson(List<Person> personList, String... ListAllExcept)  {
        SimpleBeanPropertyFilter filtreUrl = SimpleBeanPropertyFilter.filterOutAllExcept(ListAllExcept);
        FilterProvider list = new SimpleFilterProvider().addFilter("Filtre", filtreUrl);
        MappingJacksonValue personsFiltre = new MappingJacksonValue(personList);
        personsFiltre.setFilters(list);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(list);
        JsonObject jsonObject = null;

        try {
            String jsonData = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(personsFiltre);
            jsonObject =  new JsonParser().parse(jsonData).getAsJsonObject();
            return jsonObject;
        }catch (JsonProcessingException e){
            logger.error(e.getMessage());
        }
        return jsonObject;
    }

    public JsonObject filtreAllExceptListFirestation(List<FireStation> fireStationList, String... ListAllExcept)  {

        SimpleBeanPropertyFilter filtreUrl = SimpleBeanPropertyFilter.filterOutAllExcept(ListAllExcept);
        FilterProvider list = new SimpleFilterProvider().addFilter("FiltreFire", filtreUrl);
        MappingJacksonValue firestationsFiltre = new MappingJacksonValue(fireStationList);
        firestationsFiltre.setFilters(list);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(list);
        JsonObject jsonObject = null;

        try {
            String jsonData = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(firestationsFiltre);
            jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        }catch (JsonProcessingException e){
            logger.error(e.getMessage());
        }
        return jsonObject;

    }

    public JsonObject filtreAllExceptFirestation(FireStation fireStationList, String... ListAllExcept)  {

        SimpleBeanPropertyFilter filtreUrl = SimpleBeanPropertyFilter.filterOutAllExcept(ListAllExcept);
        FilterProvider list = new SimpleFilterProvider().addFilter("FiltreFire", filtreUrl);
        MappingJacksonValue firestationsFiltre = new MappingJacksonValue(fireStationList);
        firestationsFiltre.setFilters(list);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(list);
        JsonObject jsonObject = null;
        try {
            String jsonData = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(firestationsFiltre);

            jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        }catch (JsonProcessingException e){
            logger.error(e.getMessage());
        }

        return jsonObject;

    }


}
