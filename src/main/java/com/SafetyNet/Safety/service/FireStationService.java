package com.SafetyNet.Safety.service;


import com.SafetyNet.Safety.model.FireStation;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

    private static List<FireStation> listFirestations = new ArrayList<>();

    public List<FireStation> findAll() { return listFirestations; }

    public FireStation findById(int id){ return listFirestations.stream().filter(fireStation -> id== fireStation.getStation()).findAny().orElse(null); }

    public void save(FireStation fireStation) { listFirestations.add(fireStation); }

    public void remove(FireStation fireStation){ listFirestations.removeIf(fireStation::equals); }

    public void addaddress(FireStation fireStation, int id){
        FireStation fir =  listFirestations.stream()
                .filter(sfireStation -> id== sfireStation.getStation())
                .findAny().orElse(null);
        if (fir != null){
            fir.addAddress(fireStation.getAddress().get(0));
        }
    }

    //TODO Return à ajouter
    public void update(FireStation fireStations, int id){
         listFirestations.stream()
                .filter(fireStation -> id == fireStation.getStation())
                .forEach(fireStation -> fireStation.setAddress(fireStations.getAddress()));
    }
}


