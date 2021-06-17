package com.SafetyNet.Safety.controller;


import com.SafetyNet.Safety.service.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordsController {

    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @PostMapping(value = "/medicalRecords")
    public void medicalRecordPost(){

    }
    @PutMapping(value = "/medicalRecords")
    public void medicalRecordDPut(){
        //TODO
    }
    @DeleteMapping(value = "/medicalRecords")
    public void medicalRecordDelete(){

    }

}
