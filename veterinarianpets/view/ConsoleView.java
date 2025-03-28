/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.veterinarianpets.view;



/**
 *
 * @author Juliany
 */




import com.mycompany.veterinarianpets.model.Supply;
import com.mycompany.veterinarianpets.model.SupplyDAO;
import com.mycompany.veterinarianpets.model.PetAdoption;
import com.mycompany.veterinarianpets.model.Adopter;
import com.mycompany.veterinarianpets.model.Medicine;
import com.mycompany.veterinarianpets.model.Supplier;

public class ConsoleView {
    public void showMessage(String message) {
        System.out.println(message);
    }
    
    public void showPetAdoptionSuccess(PetAdoption pet, Adopter adopter) {
        showMessage("Mascota registrada: " + pet.getName() + ", ID: " + pet.getId());
        showMessage("Adoptante registrado: " + adopter.getFullName() + ", ID: " + adopter.getId());
    }
    
    public void showMedicineSuccess(Medicine medicine, Supplier supplier) {
        showMessage("Medicamento registrado: " + medicine.getName() + ", Stock: " + medicine.getStock());
        showMessage("Proveedor: " + supplier.getName() + ", ID: " + supplier.getId());
    }
    
    public void showSupplySuccess(Supply supply) throws java.sql.SQLException {
        showMessage("Insumo registrado: " + supply.getType() + ", Stock: " + supply.getStock());
        SupplyDAO dao = new SupplyDAO();
        if (dao.isNearExpiration(supply.getExpirationDate())) {
            showMessage("Alerta: Este insumo está próximo a vencer.");
        }
    }
    
    public void showOrderGenerated(String itemType, int itemId, int quantity) {
        showMessage("Orden generada para " + itemType + " ID: " + itemId + ", Cantidad: " + quantity);
    }
    
    public void showError(String error) {
        showMessage("Error: " + error);
    }
}
    

    
    

