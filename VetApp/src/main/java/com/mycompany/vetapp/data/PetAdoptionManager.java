/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vetapp.data;

/**
 *
 * @author Juliany
 */


import java.sql.*;
import java.util.Date;

public class PetAdoptionManager {
    
    private Connection connection;
    
    public PetAdoptionManager(Connection conn) {
        this.connection = conn;
    }
    
    public int registerPet(String name, String species, String race, 
                         int age, Date birthDate, String gender, 
                         String description, String photoUrl) throws SQLException {
        String query = "INSERT INTO PetAdoption (name, species, race, age, birthDate, " +
                      "gender, description, photo, entryDate) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURDATE())";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, 
             Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, name);
            stmt.setString(2, species);
            stmt.setString(3, race);
            stmt.setInt(4, age);
            stmt.setDate(5, birthDate != null ? new java.sql.Date(birthDate.getTime()) : null);
            stmt.setString(6, gender);
            stmt.setString(7, description);
            stmt.setString(8, photoUrl);
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        }
    }
    
    public boolean registerMedical(int petId, String medicalDetails) throws SQLException {
        String query = "INSERT INTO MedicalHistory (petAdoption_id, details) " +
                      "VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, petId);
            stmt.setString(2, medicalDetails);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public int registerAdopter(String personalId, String fullName, String address, 
                              String cellphone, String email, String reference) 
                              throws SQLException {
        String query = "INSERT INTO Adopter (personalId, fullName, address, " +
                      "cellphone, email, personalReference) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, 
             Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, personalId);
            stmt.setString(2, fullName);
            stmt.setString(3, address);
            stmt.setString(4, cellphone);
            stmt.setString(5, email);
            stmt.setString(6, reference);
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        }
    }
    
    public boolean registerFullAdoption(String petName, String species, String race, 
                                       int age, Date birthDate, String gender,
                                       String description, String photoUrl,
                                       String medicalDetails, String adopterPersonalId, 
                                       String adopterFullName, String adopterAddress, 
                                       String adopterCellphone, String adopterEmail, 
                                       String adopterReference) throws SQLException {
        try {
            connection.setAutoCommit(false);
            
            int petId = registerPet(petName, species, race, age, birthDate, 
                                   gender, description, photoUrl);
            if (petId == -1) {
                throw new SQLException("Error al registrar mascota");
            }
            
            if (!registerMedical(petId, medicalDetails)) {
                throw new SQLException("Error al registrar historial medico");
            }
            
            int adopterId = registerAdopter(adopterPersonalId, adopterFullName, 
                                           adopterAddress, adopterCellphone, 
                                           adopterEmail, adopterReference);
            if (adopterId == -1) {
                throw new SQLException("Error registrar adoptante");
            }
            
            connection.commit();
            return true;
            
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
    

