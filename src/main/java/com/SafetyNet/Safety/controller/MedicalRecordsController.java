package com.SafetyNet.Safety.controller;

import com.SafetyNet.Safety.model.Person;
import com.SafetyNet.Safety.service.PersonService;
import com.SafetyNet.Safety.util.BuilderResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordsController {
    private final static Logger logger = LogManager.getLogger("MedicalController") ;

    @Autowired
    private PersonService personService;

    private BuilderResponse builderResponse = new BuilderResponse();

    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<?> medicalRecordPost(@RequestBody Person person) {
        logger.info("Post /medicalRecord");
        return builderResponse.responseBoolean(personService.personMedicalPost(person));
    }

    @PutMapping (value = "/medicalRecord")
    public ResponseEntity<?> medicalRecordPut(@RequestBody Person person) {
        logger.info("Put /medicalRecord");
        return builderResponse.responseBoolean(personService.personMedicalPut(person));
    }

    @DeleteMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<?> medicalRecordDelete(@PathVariable String firstName,@PathVariable String lastName) {
        logger.info("Delete /medicalRecord");
        return builderResponse.responseBoolean(personService.personMedicalDelete(firstName,lastName));
    }

}
