package Main;

import Controller.HealthHistorialController;
import Controller.MedicalConsultationController;
import Controller.MedicineController;
import Controller.OwnerPetController;
import Controller.PetAdoptionController;
import Controller.SupplierController;
import Controller.SupplyController;
import Controller.VeterinarianController;
import Model.HealthHistorialDAO;
import Model.MedicalConsultationDAO;
import Model.MedicineDAO;
import Model.OwnerDAO;
import Model.PetDAO;
import Model.SupplierDAO;
import Model.VeterianarianDAO;
import View.ConsultationPlanMenu;
import View.HealthHistorialView;
import View.InventoryMenu;
import View.MedicalConsultationView;
import View.MedicineView;
import View.OwnerView;
import View.PetAdoptionView;
import View.PetView;
import View.SupplierView;
import View.SupplyView;
import View.VeterinarianView;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Inicialización de DAOs
            OwnerDAO ownerDAO = new OwnerDAO();
            PetDAO petDAO = new PetDAO();
            HealthHistorialDAO historialDAO = new HealthHistorialDAO();
            VeterianarianDAO veterinarianDAO = new VeterianarianDAO();
            MedicalConsultationDAO medicalConsultationDAO = new MedicalConsultationDAO();
            SupplierDAO supplierDAO = new SupplierDAO();
            MedicineDAO medicineDAO = new MedicineDAO();

            // Inicialización de Views
            OwnerView ownerView = new OwnerView();
            PetView petView = new PetView();
            HealthHistorialView historialView = new HealthHistorialView();
            VeterinarianView veterinarianView = new VeterinarianView();
            MedicalConsultationView medicalConsultationView = new MedicalConsultationView();
            PetAdoptionView adoptionView = new PetAdoptionView();
            SupplierView supplierView = new SupplierView();
            MedicineView view = new MedicineView();
            SupplyView supplyView = new SupplyView();

            // Inicialización de Controllers
            OwnerPetController ownerPetController = new OwnerPetController(ownerDAO, petDAO, ownerView, petView);
            HealthHistorialController historialController = new HealthHistorialController(historialDAO, petDAO, historialView);
            VeterinarianController veterinarianController = new VeterinarianController(veterinarianView, veterinarianDAO);
            MedicalConsultationController medicalConsultationController = new MedicalConsultationController(medicalConsultationView);
            PetAdoptionController adoptionController = new PetAdoptionController();
            SupplierController supplierController = new SupplierController();
            MedicineController medicineController = new MedicineController(view);
            SupplyController supplyController = new SupplyController();
            InventoryMenu inventoryMenu = new InventoryMenu(medicineController, supplierController,supplyController);

            boolean exit = false;
            
            while (!exit) {
                System.out.println("\n================ MENÚ PRINCIPAL ================");
                System.out.println("1. Módulo de Dueños y Mascotas");
                System.out.println("2. Módulo de Historial de Salud");
                System.out.println("3. Módulo de Consultas Médicas");
                System.out.println("4. Módulo de Inventario (Medicinas y Proveedores)");
                System.out.println("5. Módulo de Facturación");
                System.out.println("6. Módulo de Adopciones");
                System.out.println("7. Módulo de Veterinarios");
                System.out.println("8. Salir");
                System.out.print("\nSeleccione una opción: ");
                
                int option;
                try {
                    option = Integer.parseInt(sc.nextLine());
                    
                    switch (option) {
                        case 1:
                            ownerPetController.OwnerPet();
                            break;
                        case 2:
                            historialController.iniciarHealthHistorial();
                            break;
                        case 3:
                            new ConsultationPlanMenu().showMenu();
                            break;
                        case 4:
                            inventoryMenu.showMenu();                             
                            break;
                        case 5:
                            // Módulo de Facturación
                            break;
                        case 6:
                            adoptionController.startAdoptionModule();
                            break;
                        case 7:
                            veterinarianController.iniciarVeterinarian();
                            break;
                        case 8:
                            exit = true;
                            System.out.println("Saliendo del sistema...");
                            break;
                        default:
                            System.out.println("Opción no válida");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión con la base de datos: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    private static void showInventoryMenu(MedicineController medicineController, SupplierController supplierController) {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n=== MÓDULO DE INVENTARIO ===");
            System.out.println("1. Gestión de Medicamentos");
            System.out.println("2. Gestión de Proveedores");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            try {
                int option = Integer.parseInt(sc.nextLine());
                
                switch (option) {
                    case 1:
                        //medicineController.showMedicineMenu();
                        break;
                    case 2:
                        supplierController.showSupplierMenu();
                        break;
                    case 3:
                        back = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido");
            }
        }
    }
}