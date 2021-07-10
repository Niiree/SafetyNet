package com.SafetyNet.Safety.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@JsonFilter("Filtre") //TODO Blocage lors d'appel sans filtre
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private Date birthdate;
    private List<String> allergies;
    private List<String> medical;


    public Person() {
    }

    public Person( String firstName, String lastName, String address,String city, String zip, String phone, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.medical = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMedical() {
        return medical;
    }

    public void setMedical(List<String> medical) { this.medical = medical; }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public boolean isAdult(){
        Date dateNow = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateNow = new SimpleDateFormat("dd/MM/yyyy");
        long between = dateNow.getTime() - birthdate.getTime();

        if (between/(1000*60*60*24) > 6570 ){
            return true;
        }
        return false;
    }
}



