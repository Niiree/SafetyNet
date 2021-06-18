package com.SafetyNet.Safety.model;

import java.util.ArrayList;
import java.util.List;

public class FireStation {
    private int station;
    private List<String> address = new ArrayList<String>();

    public FireStation(List<String> address, int station) {
        this.station = station;
        this.address = address;
    }

    public FireStation() { }

    public FireStation(FireStation fireStation) { }

    public int getStation() { return station; }

    public void setStation(int station) {
        this.station = station;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }
    public void addAddress(String address){
        this.address.add(address);
    }
}
