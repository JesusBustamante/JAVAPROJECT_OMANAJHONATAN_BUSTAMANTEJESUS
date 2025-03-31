package View;

import Model.Adopter;
import Model.Medicine;
import Model.PetAdoption;
import Model.Supplier;
import Model.Supply;
import Model.SupplyDAO;
import java.util.List;

public class ConsoleView {
    public void showMessage(String message) {
        System.out.println(message);
    }
    
    public void showPetAdoptionSuccess(PetAdoption pet, Adopter adopter) {
        System.out.println("\n=== ADOPCIÓN REGISTRADA CON ÉXITO ===");
        System.out.println("Mascota: " + pet.getName() + " (" + pet.getSpecies() + ")");
        System.out.println("Adoptante: " + adopter.getFullName());
        System.out.println("ID Mascota: " + pet.getId());
        System.out.println("ID Adoptante: " + adopter.getId());
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

    public void showAvailablePets(List<PetAdoption> pets) {
        System.out.println("\n=== MASCOTAS DISPONIBLES ===");
        if (pets.isEmpty()) {
            System.out.println("No hay mascotas disponibles actualmente");
            return;
        }

        for (PetAdoption pet : pets) {
            System.out.printf("%d. %s - %s (%s) %n", 
                pet.getId(), pet.getName(), pet.getSpecies(), pet.getGender());
            System.out.println("   Edad: " + pet.getAge() + " años");
            System.out.println("   Descripción: " + pet.getDescription());
            System.out.println("-----------------------------------");
        }
    }
}
    

    
    

