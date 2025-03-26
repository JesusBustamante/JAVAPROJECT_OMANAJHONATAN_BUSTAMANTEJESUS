/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medicalsuppliesregistry.data;

/**
 *
 * @author Uniminuto Tibu
 */


import java.sql.*;
import java.util.Date;

public class SupplyManager {
    
    private Connection connection;
    
    public SupplyManager(Connection conn) {
        this.connection = conn;
    }
    
    // Registrar un insumo médico
    public boolean registerSupply(String type, String pack, Date entryDate, 
                                 Date expirationDate, int supplierId) throws SQLException {
        // Verificar si la fecha de vencimiento es válida
        Date today = new Date();
        if (expirationDate.before(today)) {
            throw new SQLException("No se puede registrar el suministro caducado");
        }
        
        String query = "INSERT INTO Supply (type, pack, entryDate, expirationDate, supplier_id) " +
                      "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, type);
            stmt.setString(2, pack);
            stmt.setDate(3, new java.sql.Date(entryDate.getTime()));
            stmt.setDate(4, new java.sql.Date(expirationDate.getTime()));
            stmt.setInt(5, supplierId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    // Verificar si un insumo está próximo a vencer (30 días o menos)
    public boolean isNearExpiration(Date expirationDate) {
        Date today = new Date();
        long diffInMillies = expirationDate.getTime() - today.getTime();
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
        return diffInDays <= 30 && diffInDays >= 0;
    }
    
    // Registrar insumo con distribuidor existente
    public boolean registerFullSupply(String type, String pack, Date entryDate, 
                                     Date expirationDate, int supplierId) throws SQLException {
        try {
            connection.setAutoCommit(false);
            
            boolean success = registerSupply(type, pack, entryDate, expirationDate, supplierId);
            if (!success) {
                throw new SQLException("Error al registrar el suministro");
            }
            
            if (isNearExpiration(expirationDate)) {
                System.out.println("Alerta: El insumo " + type + " está próximo a vencer.");
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
    

