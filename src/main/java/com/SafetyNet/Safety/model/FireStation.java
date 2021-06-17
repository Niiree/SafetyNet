package com.SafetyNet.Safety.model;

import java.util.ArrayList;
import java.util.List;

public class FireStation {
    private List<String> address = new ArrayList<String>();
    private int station;

    public FireStation(List<String> address, int station) {
        this.address = address;
        this.station = station;
    }

    public FireStation() {
    }

    public FireStation(FireStation s) {
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address.add(address);
    }

    public int getIdStation() { return station;
    }

    public void setIdStation(int station) {
        this.station = station;
    }
}
