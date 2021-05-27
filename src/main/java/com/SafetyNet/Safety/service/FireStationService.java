package com.SafetyNet.Safety.service;


import com.SafetyNet.Safety.model.FireStation;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

    private static List<FireStation> firestations = new ArrayList<>();

    public List<FireStation> findAll() {
        return firestations;
    }

    public void saveFireStation(FireStation fireStation) { firestations.add(fireStation); }
}
