package View; 

import Controller.MedicineController;
import Controller.SupplierController;
import Controller.SupplyController;
import java.util.Scanner;

public class InventoryMenu {
    private Scanner scanner;
    private MedicineController medicineController;
    private SupplierController supplierController;
    private SupplyController supplyController;

    public InventoryMenu(MedicineController medicineController, SupplierController supplierController, SupplyController supplyController) {
        this.scanner = new Scanner(System.in);
        this.medicineController = medicineController;
        this.supplierController = supplierController;
        this.supplyController = supplyController;
    }

    public void showMenu() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n=== MENÚ DE INVENTARIO ===");
            System.out.println("1. Gestión de Medicamentos");
            System.out.println("2. Gestión de Proveedores");
            System.out.println("3. Gestión de Insumos");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            try {
                int option = Integer.parseInt(scanner.nextLine());
                
                switch (option) {
                    case 1:
                        medicineController.showMedicineMenu(); 
                        break;
                    case 2:
                        supplierController.showSupplierMenu(); 
                        break;
                    case 3:
                        supplyController.showSupplyMenu();
                        break;
                    case 4:
                        back = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }
    }
}