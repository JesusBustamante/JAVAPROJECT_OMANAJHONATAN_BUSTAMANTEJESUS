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

public class MedicineDAO {
    public Connection connection;
    
    public MedicineDAO() throws SQLException {
        this.connection = DBConnect.getConnection();
    }
    
    public int insertSupplier(Supplier supplier) throws SQLException {
        String query = "INSERT INTO Supplier (name, address, cellphone, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getAddress());
            stmt.setString(3, supplier.getCellphone());
            stmt.setString(4, supplier.getEmail());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                supplier.setId(rs.getInt(1));
                return rs.getInt(1);
            }
            return -1;
        }
    }
    
    public boolean insertMedicine(Medicine medicine) throws SQLException {
        String query = "INSERT INTO Medicine (name, type, manufacturer, stock, expirationDate, supplier_id) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, medicine.getName());
            stmt.setString(2, medicine.getType());
            stmt.setString(3, medicine.getManufacturer());
            stmt.setInt(4, medicine.getStock());
            stmt.setDate(5, new java.sql.Date(medicine.getExpirationDate().getTime()));
            stmt.setInt(6, medicine.getSupplierId());
            return stmt.executeUpdate() > 0;
        }
    }
}
    

