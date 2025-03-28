/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.veterinarianpets.controller;

/**
 *
 * @author Juliany
 */



import com.mycompany.veterinarianpets.model.Medicine;
import com.mycompany.veterinarianpets.model.MedicineDAO;
import com.mycompany.veterinarianpets.model.Supplier;
import com.mycompany.veterinarianpets.view.ConsoleView;
import java.sql.SQLException;
import java.util.Date;

public class MedicineController {
    private MedicineDAO dao;
    private ConsoleView view;
    
    public MedicineController() throws SQLException {
        this.dao = new MedicineDAO();
        this.view = new ConsoleView();
    }
    
    public void registerMedicine(String medName, String medType, String manufacturer, int stock, 
                                Date expirationDate, String supplierName, String supplierAddress, 
                                String supplierCellphone, String supplierEmail) {
        try {
            if (expirationDate.before(new Date())) {
                throw new IllegalArgumentException("Expiration date cannot be in the past");
            }
            if (stock < 0) {
                throw new IllegalArgumentException("Stock cannot be negative");
            }
            
            Supplier supplier = new Supplier(supplierName, supplierAddress, supplierCellphone, supplierEmail);
            Medicine medicine = new Medicine(medName, medType, manufacturer, stock, expirationDate, 0);
            
            dao.connection.setAutoCommit(false);
            int supplierId = dao.insertSupplier(supplier);
            if (supplierId == -1) {
                throw new SQLException("Failed to register supplier");
            }
            medicine.setSupplierId(supplierId);
            if (!dao.insertMedicine(medicine)) {
                throw new SQLException("Failed to register medicine");
            }
            dao.connection.commit();
            view.showMedicineSuccess(medicine, supplier);
        } catch (SQLException e) {
            try {
                dao.connection.rollback();
            } catch (SQLException ex) {
                view.showError("Rollback failed: " + ex.getMessage());
            }
            view.showError(e.getMessage());
        } catch (IllegalArgumentException e) {
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
    

