/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vetapp.control;

/**
 *
 * @author Juliany
 */


import com.mycompany.vetapp.data.PetAdoptionManager;
import com.mycompany.vetapp.db.DBConnect;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class AdoptionHandler {
    private PetAdoptionManager manager;
    
    public AdoptionHandler() {
        try {
            Connection conn = DBConnect.getConnection();
            this.manager = new PetAdoptionManager(conn);
        } catch (SQLException e) {
            System.out.println("DB connection failed: " + e.getMessage());
        }
    }
    
    public boolean handleAdoption(String petName, String species, String race, int age, 
                                Date birthDate, String gender, String description, 
                                String photoUrl, String medicalDetails, 
                                String adopterPersonalId, String adopterFullName,
                                String adopterAddress, String adopterCellphone, 
                                String adopterEmail, String adopterReference) {
        try {
            return manager.registerFullAdoption(petName, species, race, age, birthDate, 
                                               gender, description, photoUrl, medicalDetails, 
                                               adopterPersonalId, adopterFullName, 
                                               adopterAddress, adopterCellphone, 
                                               adopterEmail, adopterReference);
        } catch (SQLException e) {
            System.out.println("Error de adopcion: " + e.getMessage());
            return false;
        }
    }
}
    

