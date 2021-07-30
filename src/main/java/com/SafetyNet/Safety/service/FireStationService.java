package com.SafetyNet.Safety.service;


import com.SafetyNet.Safety.model.FireStation;

import com.SafetyNet.Safety.util.Filtre;
import com.SafetyNet.Safety.util.exceptions.FireStationIntrouvableException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private Filtre filtre = new Filtre();

    private static List<FireStation> listFirestations = new ArrayList<>();

    public List<FireStation> findAll() {
        return listFirestations;
    }

    public FireStation findById(int id) {
        return listFirestations.stream().filter(fireStation -> id == fireStation.getStation()).findAny().orElse(null);
    }

    public List<FireStation> findByAddress(String address) {
        return listFirestations.stream().filter(fireStation -> fireStation.getAddress().contains(address)).collect(Collectors.toList());
    }

    public void save(FireStation fireStation) {
        listFirestations.add(fireStation);
    }

    public boolean remove(FireStation fireStation) {
        Optional<FireStation> resultFirestation = listFirestations.stream().filter(fireStation1 -> fireStation == fireStation1).findAny();
        if (resultFirestation == null) {
            throw new FireStationIntrouvableException("Firestation introuvable");
        } else {
            listFirestations.removeIf(fireStation1 -> fireStation.getStation() == fireStation1.getStation());
            return true;
        }
    }

    public boolean addAddress(String address, int id) {
        FireStation fir = this.findById(id);
        if (fir != null) {
            fir.addAddressList(address);
            return true;
        } else {
            throw new FireStationIntrouvableException("Firestation introuvable");
        }

    }

    //TODO Return à ajouter
    public boolean update(FireStation fireStations, int id) throws JsonProcessingException {
        FireStation fire = listFirestations.stream()
                .filter(fireStation -> id == fireStation.getStation())
                .findAny()
                .orElse(null);
        if (fire != null) {
            fire.setAddress(fireStations.getAddress());
            return true;
        } else {
            throw new FireStationIntrouvableException("Firestation introuvable");
        }

    }


}


