package com.SafetyNet.Safety.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@JsonFilter("Filtre")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private String birthdate;
    private List<String> allergies;
    private List<String> medical;

    private final static Logger logger = LogManager.getLogger("BuilderReponse") ;


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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate.toString();}

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public Boolean isAdult() {
        Date dateNow = new Date(System.currentTimeMillis());
        try {
            Date Formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(this.birthdate);
            long between = dateNow.getTime() - Formatter.getTime();
            if (between/(1000*60*60*24) > 6570 ){
                return true;
            }
            return false;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

}



