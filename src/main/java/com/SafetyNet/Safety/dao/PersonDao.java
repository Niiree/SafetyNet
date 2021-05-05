package com.SafetyNet.Safety.dao;

import com.SafetyNet.Safety.model.Person;

import java.util.List;

public interface PersonDao {
    public List<Person> findAll();
    public Person PersonfindById(int id);
    public void PersonSave(Person person);

}
