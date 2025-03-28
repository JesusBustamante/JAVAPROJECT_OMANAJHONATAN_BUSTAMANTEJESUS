/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.veterinarianpets.controller;

/**
 *
 * @author Juliany
 */


import java.sql.SQLException;
import java.util.Date;

public class MainController {
    private PetAdoptionController petController;
    private MedicineController medController;
    private SupplyController supplyController;
    private SupplierController supplierController;
    
    public MainController() throws SQLException {
        petController = new PetAdoptionController();
        medController = new MedicineController();
        supplyController = new SupplyController();
        supplierController = new SupplierController();
    }
    
    public void run() {
        Date today = new Date();
        Date future = new Date(today.getTime() + 60 * 24 * 60 * 60 * 1000L); 
        
        // Registro de mascota y adoptante
        petController.registerPetAndAdopter("Luna", "dog", "Labrador", 2, null, "FEMALE", 
                                           "Perra amistosa", "luna.jpg", "Sana", 
                                           "12345", "Juan Pérez", "Calle 123", "555-1234", 
                                           "juan@example.com", "Amigo");

        // Registro de medicamento
        medController.registerMedicine("Paracetamol", "Analgésico", "Pfizer", 5, future, 
                                      "Proveedor A", "Av. Principal", "555-5678", "prov@example.com");

        // Registro de insumo (suponiendo supplierId=1 del paso anterior)
        supplyController.registerSupply("Gasa", "10 unidades", 3, today, future, 1);

        // Verificación de stock y órdenes
        supplierController.checkStockAndOrder(1, 1);
    }
    
    public static void main(String[] args) {
        try {
            new MainController().run();
        } catch (SQLException e) {
            System.out.println("Initialization error: " + e.getMessage());
        }
    }
}

