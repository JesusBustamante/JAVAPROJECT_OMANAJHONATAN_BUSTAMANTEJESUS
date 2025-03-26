/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.adoptionpet.control;

/**
 *
 * @author Juliany
 */
import com.mycompany.adoptionpet.data.MedicineManager;
import com.mycompany.adoptionpet.db.DBConnect;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class MedicineHandler {
    private MedicineManager manager;
    
    public MedicineHandler() {
        try {
            Connection conn = DBConnect.getConnection();
            this.manager = new MedicineManager(conn);
        } catch (SQLException e) {
            System.out.println("La conexion falló: " + e.getMessage());
        }
    }
    
    public boolean handleMedicineRegistration(String medName, String medType, String manufacturer, 
                                            int stock, Date expirationDate, String supplierName, 
                                            String supplierAddress, String supplierCellphone, 
                                            String supplierEmail) {
        try {
            return manager.registerFullMedicine(medName, medType, manufacturer, stock, 
                                               expirationDate, supplierName, supplierAddress, 
                                               supplierCellphone, supplierEmail);
        } catch (SQLException e) {
            System.out.println("Error de registro de medicamentos: " + e.getMessage());
            return false;
        }
    }
}
    

