/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adoptionpet.data;

/**
 *
 * @author Juliany
 */
import java.sql.*;
import java.util.Date;

public class MedicineManager {
    
    private Connection connection;
    
    public MedicineManager(Connection conn) {
        this.connection = conn;
    }
    
    // Registrar un distribuidor
    public int registerSupplier(String name, String address, String cellphone, String email) 
                               throws SQLException {
        String query = "INSERT INTO Supplier (name, address, cellphone, email) " +
                      "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query, 
             Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, cellphone);
            stmt.setString(4, email);
            
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
    
    // Registrar un medicamento con restricciones
    public boolean registerMedicine(String name, String type, String manufacturer, 
                                  int stock, Date expirationDate, int supplierId) 
                                  throws SQLException {
        // Verificar si la fecha de vencimiento es válida
        Date today = new Date();
        if (expirationDate.before(today)) {
            throw new SQLException("No se puede registrar medicamento vencido");
        }
        
        // Verificar stock
        if (stock <= 0) {
            throw new SQLException("El sttock debe ser mayor de 0");
        }
        
        String query = "INSERT INTO Medicine (name, type, manufacturer, stock, expirationDate, supplier_id) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setString(3, manufacturer);
            stmt.setInt(4, stock);
            stmt.setDate(5, new java.sql.Date(expirationDate.getTime()));
            stmt.setInt(6, supplierId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    // Registrar distribuidor y medicamento juntos
    public boolean registerFullMedicine(String medName, String medType, String manufacturer, 
                                       int stock, Date expirationDate, String supplierName, 
                                       String supplierAddress, String supplierCellphone, 
                                       String supplierEmail) throws SQLException {
        try {
            connection.setAutoCommit(false);
            
            // Registrar distribuidor primero
            int supplierId = registerSupplier(supplierName, supplierAddress, 
                                            supplierCellphone, supplierEmail);
            if (supplierId == -1) {
                throw new SQLException("Error al registrar proveedor");
            }
            
            // Registrar medicamento con el ID del distribuidor
            boolean success = registerMedicine(medName, medType, manufacturer, 
                                             stock, expirationDate, supplierId);
            if (!success) {
                throw new SQLException("Error al registrar medicina");
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
    

