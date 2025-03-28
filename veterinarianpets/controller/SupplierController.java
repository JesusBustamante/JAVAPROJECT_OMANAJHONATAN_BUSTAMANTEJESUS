/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.veterinarianpets.controller;

/**
 *
 * @author Juliany
 */


import com.mycompany.veterinarianpets.model.SupplierDAO;
import com.mycompany.veterinarianpets.view.ConsoleView;
import java.sql.SQLException;

public class SupplierController {
    private SupplierDAO dao;
    private ConsoleView view;
    
    public SupplierController() throws SQLException {
        this.dao = new SupplierDAO();
        this.view = new ConsoleView();
    }
    
    public void checkStockAndOrder(int medicineId, int supplyId) {
        try {
            boolean medOrder = dao.checkMedicineStockAndOrder(medicineId);
            if (medOrder) {
                view.showOrderGenerated("Medicine", medicineId, 20); // Cantidad ejemplo
            }
            boolean supplyOrder = dao.checkSupplyStockAndOrder(supplyId);
            if (supplyOrder) {
                view.showOrderGenerated("Supply", supplyId, 10); // Cantidad ejemplo
            }
            if (!medOrder && !supplyOrder) {
                view.showMessage("No orders needed at this time.");
            }
        } catch (SQLException e) {
            view.showError(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
    

