package Main;

import Controller.HealthHistorialController;
import Controller.HealthHistorialController;
import Controller.OwnerPetController;
import Controller.VeterinarianController;
import Model.HealthHistorialDAO;
//import Model.HealthHistorialDAO;
import Model.OwnerDAO;
import Model.PetDAO;
import Model.VeterianarianDAO;
import View.HealthHistorialView;
import View.HealthHistorialView;
import View.OwnerView;
import View.PetView;
import View.VeterinarianView;
import java.util.Scanner;

public class Main {
    
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {

        // Instancias de los DAOs
        OwnerDAO ownerDAO = new OwnerDAO();
        PetDAO petDAO = new PetDAO();
        HealthHistorialDAO historialDAO = new HealthHistorialDAO();
        VeterianarianDAO veterinarianDAO = new VeterianarianDAO();
        
        // Instancias de los views
        OwnerView ownerView = new OwnerView();
        PetView petView = new PetView();
        HealthHistorialView historialView = new HealthHistorialView();
        VeterinarianView veterinarianView = new VeterinarianView();

        // Crear el controlador y pasarle los componentes
        OwnerPetController ControllerOwnerPet = new OwnerPetController(ownerDAO, petDAO, ownerView, petView);
        HealthHistorialController historialController = new HealthHistorialController(historialDAO, petDAO, historialView);
        VeterinarianController veterinarianController = new VeterinarianController(veterinarianView, veterinarianDAO);

        System.out.println("================ MENÚ PRINCIPAL ================\n");
        
        System.out.println("1. OWNERS AND PETS REGISTRATION MODULE");
        System.out.println("2. PET HEALTH MODULE");
        System.out.println("3. MEDICAL CONSULTATIONS AND PROCEDURES MODULE");
        System.out.println("4. MEDICINES AND SUPPLIES INVENTORY MODULE");
        System.out.println("5. INVOICING AND REPORTING MODULE");
        System.out.println("6. ADOPTION MODULE");
        System.out.println("7. VETERINARY MODULE");

        System.out.print("\nSelect an option: ");
        int option = sc.nextInt();
        sc.nextLine();
        
        switch(option) {
            case 1:
                ControllerOwnerPet.OwnerPet();
                break;
            case 2:
                historialController.registrarHistorialMedico();
                historialView.mostrarHistoriales();
                break;
            case 3:
                
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                veterinarianController.iniciarVeterinarian();
                break;
            default:
                break;
        }
    
    }
    
}
