package com.SafetyNet.Safety.service;


import com.SafetyNet.Safety.model.FireStation;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private static List<FireStation> firestations = new ArrayList<>();

    public List<FireStation> findAll() {
        return firestations;
    }

    public void saveFireStation(FireStation fireStation) { firestations.add(fireStation); }

    public void removeFireStation(FireStation fireStation){ firestations.removeIf(fireStation::equals); }

    public void updateFireStation(FireStation fireStations,int id){
        FireStation fir =  firestations.stream().filter(fireStation -> id== fireStation.getIdStation()).findAny().orElse(null);

        if (fir != null){
        //    if(fireStations.getIdStation() != null){
                fir.setIdStation(fireStations.getIdStation());
            }
        }
    }


