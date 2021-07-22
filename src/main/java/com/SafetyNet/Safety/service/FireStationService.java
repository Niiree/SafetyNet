package com.SafetyNet.Safety.service;


import com.SafetyNet.Safety.model.FireStation;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private static List<FireStation> listFirestations = new ArrayList<>();

    public List<FireStation> findAll() { return listFirestations; }

    public FireStation findById(int id){ return listFirestations.stream().filter(fireStation -> id== fireStation.getStation()).findAny().orElse(null); }

    public List<FireStation> findByAddress(String address){ return listFirestations.stream().filter(fireStation -> fireStation.getAddress().contains(address)).collect(Collectors.toList());}

    public void save(FireStation fireStation) { listFirestations.add(fireStation); }

    public boolean remove(FireStation fireStation){
        Optional<FireStation> resultFirestation = listFirestations.stream().filter(fireStation1 -> fireStation == fireStation1).findAny();
        if (resultFirestation.equals(null)){
            return false;
        }else {
        listFirestations.removeIf(fireStation::equals);
        return true;
        }
    }

    public void addAddress(String address, int id){
        FireStation fir = this.findById(id);
        if (fir != null){
            fir.addAddressList(address);
        }
    }

    //TODO Return à ajouter
    public boolean update(FireStation fireStations, int id){
        FireStation fire = listFirestations.stream()
                .filter(fireStation -> id == fireStation.getStation())
                .findAny()
                .orElse(null);
        if (fire != null){
            fire.setAddress(fireStations.getAddress());
            return true;
        }else{
            return false;
        }

    }


}


