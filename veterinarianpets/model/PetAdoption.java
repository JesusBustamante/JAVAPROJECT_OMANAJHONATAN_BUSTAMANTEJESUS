/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.veterinarianpets.model;

/**
 *
 * @author Juliany
 */

import java.util.Date;

public class PetAdoption {
    private int id;
    private String name, species, race, gender, description, photo;
    private int age;
    private Date birthDate, entryDate;
    
    public PetAdoption(String name, String species, String race, int age, Date birthDate, 
                       String gender, String description, String photo) {
        this.name = name;
        this.species = species;
        this.race = race;
        this.age = age;
        this.birthDate = birthDate;
        this.gender = gender;
        this.description = description;
        this.photo = photo;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public Date getEntryDate() { return entryDate; }
    public void setEntryDate(Date entryDate) { this.entryDate = entryDate; }
}
    
    

