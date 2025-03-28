/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.veterinarianpets.model;

/**
 *
 * @author Juliany
 */


import com.mycompany.veterinarianpets.db.DBConnect;
import java.sql.*;
import java.util.Date;

public class PetAdoptionDAO {
    public Connection connection;
    
    public PetAdoptionDAO() throws SQLException {
        this.connection = DBConnect.getConnection();
    }
    
    public int insertPet(PetAdoption pet) throws SQLException {
        String query = "INSERT INTO PetAdoption (name, species, race, age, birthDate, gender, description, photo, entryDate) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURDATE())";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pet.getName());
            stmt.setString(2, pet.getSpecies());
            stmt.setString(3, pet.getRace());
            stmt.setInt(4, pet.getAge());
            stmt.setDate(5, pet.getBirthDate() != null ? new java.sql.Date(pet.getBirthDate().getTime()) : null);
            stmt.setString(6, pet.getGender());
            stmt.setString(7, pet.getDescription());
            stmt.setString(8, pet.getPhoto());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pet.setId(rs.getInt(1));
                pet.setEntryDate(new Date()); // Simula la fecha actual
                return rs.getInt(1);
            }
            return -1;
        }
    }
    
    public boolean insertMedical(int petId, String medicalDetails) throws SQLException {
        String query = "INSERT INTO MedicalHistory (petAdoption_id, details) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, petId);
            stmt.setString(2, medicalDetails);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public int insertAdopter(Adopter adopter) throws SQLException {
        String query = "INSERT INTO Adopter (personalId, fullName, address, cellphone, email, personalReference) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, adopter.getPersonalId());
            stmt.setString(2, adopter.getFullName());
            stmt.setString(3, adopter.getAddress());
            stmt.setString(4, adopter.getCellphone());
            stmt.setString(5, adopter.getEmail());
            stmt.setString(6, adopter.getReference());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                adopter.setId(rs.getInt(1));
                return rs.getInt(1);
            }
            return -1;
        }
    }
}
    

