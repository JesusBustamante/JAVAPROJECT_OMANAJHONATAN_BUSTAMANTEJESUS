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

public class SupplyDAO {
    private Connection connection;
    
    public SupplyDAO() throws SQLException {
        this.connection = DBConnect.getConnection();
    }
    
    public boolean insertSupply(Supply supply) throws SQLException {
        String query = "INSERT INTO Supply (type, pack, stock, entryDate, expirationDate, supplier_id) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, supply.getType());
            stmt.setString(2, supply.getPack());
            stmt.setInt(3, supply.getStock());
            stmt.setDate(4, new java.sql.Date(supply.getEntryDate().getTime()));
            stmt.setDate(5, new java.sql.Date(supply.getExpirationDate().getTime()));
            stmt.setInt(6, supply.getSupplierId());
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean isNearExpiration(Date expirationDate) {
        Date today = new Date();
        long diffInMillies = expirationDate.getTime() - today.getTime();
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
        return diffInDays <= 30 && diffInDays >= 0;
    }
}
    

