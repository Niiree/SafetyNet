package com.SafetyNet.Safety.model;

public class FireStation {
    private String address;
    private int station;

    public FireStation(String address, int station) {
        this.address = address;
        this.station = station;
    }

    public FireStation() {
    }

    public FireStation(FireStation s) {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIdStation() { return station;
    }

    public void setIdStation(int station) {
        this.station = station;
    }
}
