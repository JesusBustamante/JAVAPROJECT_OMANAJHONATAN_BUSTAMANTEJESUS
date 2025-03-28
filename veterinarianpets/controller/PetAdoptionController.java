/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.veterinarianpets.controller;

/**
 *
 * @author Juliany
 */

import com.mycompany.veterinarianpets.model.Adopter;
import com.mycompany.veterinarianpets.model.PetAdoption;
import com.mycompany.veterinarianpets.model.PetAdoptionDAO;
import com.mycompany.veterinarianpets.view.ConsoleView;

import java.sql.SQLException;
import java.util.Date;

public class PetAdoptionController {
    private PetAdoptionDAO dao;
    private ConsoleView view;
    
    public PetAdoptionController() throws SQLException {
        this.dao = new PetAdoptionDAO();
        this.view = new ConsoleView();
    }
    
    public void registerPetAndAdopter(String petName, String species, String race, int age, Date birthDate, 
                                     String gender, String description, String photoUrl, String medicalDetails, 
                                     String personalId, String fullName, String address, String cellphone, 
                                     String email, String reference) {
        try {
            PetAdoption pet = new PetAdoption(petName, species, race, age, birthDate, gender, description, photoUrl);
            Adopter adopter = new Adopter(personalId, fullName, address, cellphone, email, reference);
            
            dao.connection.setAutoCommit(false);
            int petId = dao.insertPet(pet);
            if (petId == -1 || !dao.insertMedical(petId, medicalDetails)) {
                throw new SQLException("Failed to register pet or medical history");
            }
            int adopterId = dao.insertAdopter(adopter);
            if (adopterId == -1) {
                throw new SQLException("Failed to register adopter");
            }
            dao.connection.commit();
            view.showPetAdoptionSuccess(pet, adopter);
        } catch (SQLException e) {
            try {
                dao.connection.rollback();
            } catch (SQLException ex) {
                view.showError("Rollback failed: " + ex.getMessage());
            }
            view.showError(e.getMessage());
        } finally {
            try {
                dao.connection.setAutoCommit(true);
            } catch (SQLException e) {
                view.showError("Reset auto-commit failed: " + e.getMessage());
            }
        }
    }
}
    

