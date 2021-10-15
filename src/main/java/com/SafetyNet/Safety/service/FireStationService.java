package com.SafetyNet.Safety.service;

import com.SafetyNet.Safety.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private final static Logger logger = LogManager.getLogger("FirestationService");

    private static List<FireStation> listFirestations = new ArrayList<>();


    public List<FireStation> findAll() {
        return listFirestations;
    }

    public FireStation findById(int id) {
        return listFirestations.stream().filter(fireStation -> id == fireStation.getStation()).findAny().orElse(null);
    }

    /**
     * Recherche de firestation par Address
     * @param string address
     * @return Liste<Firestation> en fonction de l'addresse
     **/
    public List<FireStation> findByAddress(String address) {
        return listFirestations.stream().filter(fireStation -> fireStation.getAddress().contains(address)).collect(Collectors.toList());
    }

    /**
     * Sauvegarde Firestation
     * @param fireStation
     * @return true
     **/
    public boolean save(FireStation fireStation) {

        if( listFirestations.stream().filter(fireStation1 -> fireStation1.getStation()==(fireStation.getStation()) && fireStation1.getAddress().equals(fireStation.getAddress())).count()!=0){
            logger.error("Impossible de sauvegarder la firestation");
            return false;
        }else{
        listFirestations.add(fireStation);
        return true;
    }
    }

    /**
     * @return True ou False
     * @Param firestation
     **/
    public boolean remove(FireStation fireStation) {
        Optional<FireStation> resultFirestation = listFirestations.stream().filter(fireStation1 -> fireStation1.getStation()==fireStation.getStation()).findAny();
        if (!resultFirestation.isPresent()) {
            logger.error("Erreur sur la suppresion de la firestation");
            return false;
        } else {
            listFirestations.removeIf(fireStation1 -> fireStation.getStation() == fireStation1.getStation());
            return true;
        }
    }

    /**
     * Ajout d'un adresse
     * @return true or false
     * @Param String address & int identifiant
     **/
    public boolean addAddress(String address, int id) {
        FireStation fir = this.findById(id);
        if (fir != null) {
            fir.addAddressList(address);
            return true;
        } else {
            logger.error("Addresse non trouvée");
            return false;
        }
    }

    /**
     * Mise à jour de la firestation
     * @param fireStations & id de la firestation *
     * @return true ou false
     */
    public boolean update(FireStation fireStations, int id) {
        FireStation fire = listFirestations.stream()
                .filter(fireStation -> id == fireStation.getStation())
                .findAny()
                .orElse(null);
        if (fire != null) {
            fire.setAddress(fireStations.getAddress());
            return true;
        } else {
            logger.error("Update impossible sur la station" + fire.getStation());
            return false;
        }
    }
}


