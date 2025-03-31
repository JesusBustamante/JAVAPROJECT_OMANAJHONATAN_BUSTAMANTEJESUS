package Main;

import Controller.HealthHistorialController;
import Controller.HealthHistorialController;
import Controller.MainController;
import Controller.MedicalConsultationController;
import Controller.OwnerPetController;
import Controller.PetAdoptionController;
import Controller.VeterinarianController;
import Model.HealthHistorialDAO;
import Model.HealthHistorialDAO;
import Model.MedicalConsultationDAO;
import Model.OwnerDAO;
import Model.PetDAO;
import Model.VeterianarianDAO;
import View.ConsultationPlanMenu;
import View.HealthHistorialView;
import View.HealthHistorialView;
import View.MedicalConsultationView;
import View.OwnerView;
import View.PetAdoptionView;
import View.PetView;
import View.VeterinarianView;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        //Instancias de los DAOs
        OwnerDAO ownerDAO = new OwnerDAO();
        PetDAO petDAO = new PetDAO();
        HealthHistorialDAO historialDAO = new HealthHistorialDAO();
        VeterianarianDAO veterinarianDAO = new VeterianarianDAO();
        MedicalConsultationDAO medicalConsultationDAO = new MedicalConsultationDAO();

        //Instancias de los views
        OwnerView ownerView = new OwnerView();
        PetView petView = new PetView();
        HealthHistorialView historialView = new HealthHistorialView();
        VeterinarianView veterinarianView = new VeterinarianView();
        MedicalConsultationView medicalConsultationView = new MedicalConsultationView();
        PetAdoptionView view = new PetAdoptionView();

        //Crear el controlador y pasarle los componentes
        OwnerPetController ControllerOwnerPet = new OwnerPetController(ownerDAO, petDAO, ownerView, petView);
        HealthHistorialController historialController = new HealthHistorialController(historialDAO, petDAO, historialView);
        VeterinarianController veterinarianController = new VeterinarianController(veterinarianView, veterinarianDAO);
        MedicalConsultationController medicalConsultationController = new MedicalConsultationController(medicalConsultationView);
        PetAdoptionController adoptionController = new PetAdoptionController();
        

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

        switch (option) {
            case 1:
                ControllerOwnerPet.OwnerPet();
                break;
            case 2:
                historialController.iniciarHealthHistorial();
                break;
            case 3:
                new ConsultationPlanMenu().showMenu();
                break;
            case 4:
                
            break;
            case 5:
                break;
            case 6:
                adoptionController.startAdoptionModule();
                break;
            case 7:
                veterinarianController.iniciarVeterinarian();
                break;
            default:
                break;
        }

    }

}
