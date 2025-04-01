/*package View;

import Controller.MedicineController;
import Controller.MedicineController;
import Controller.SupplierController;
import Controller.SupplierController;

public class MedicineSupplierMenu {
    private MedicineController medicineController;
    private SupplierController supplierController;
    
    public MedicineSupplierMenu() {
        // Inicializamos los controladores con sus respectivas vistas
        this.medicineController = new MedicineController(new MedicineView());
        this.supplierController = new SupplierController(new SupplierView());
    }
    
    public void showMenu() {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Gestión de Medicamentos");
            System.out.println("2. Gestión de Proveedores");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            
            int option;
            try {
                option = Integer.parseInt(System.console().readLine());
                
                switch (option) {
                    case 1:
                        medicineController.showMedicineMenu();
                        break;
                    case 2:
                        supplierController.showSupplierMenu();
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido");
            }
        }
    }
}*/