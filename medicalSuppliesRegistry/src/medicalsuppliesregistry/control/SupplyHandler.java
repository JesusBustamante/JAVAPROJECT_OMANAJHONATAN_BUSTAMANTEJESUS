/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medicalsuppliesregistry.control;

/**
 *
 * @author Uniminuto Tibu
 */


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import medicalsuppliesregistry.data.SupplyManager;
import medicalsuppliesregistry.db.DBConnect;

public class SupplyHandler {
    private SupplyManager manager;
    
    public SupplyHandler() {
        try {
            Connection conn = DBConnect.getConnection();
            this.manager = new SupplyManager(conn);
        } catch (SQLException e) {
            System.out.println("Conexion fallida: " + e.getMessage());
        }
    }
    
    public boolean handleSupplyRegistration(String type, String pack, Date entryDate, 
                                           Date expirationDate, int supplierId) {
        try {
            return manager.registerFullSupply(type, pack, entryDate, expirationDate, supplierId);
        } catch (SQLException e) {
            System.out.println("Error al registrar el suministro: " + e.getMessage());
            return false;
        }
    }
}
    

