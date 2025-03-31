package View;

import Controller.CustomizedPlanController;
import Controller.MedicalConsultationController;
import Model.CustomizedPlan;
import Model.CustomizedPlan;
import java.util.List;
import java.util.Scanner;

public class ConsultationPlanMenu {

    private Scanner scanner;
    private MedicalConsultationController consultationController;
    private CustomizedPlanController planController;

    public ConsultationPlanMenu() {
        this.scanner = new Scanner(System.in);
        this.consultationController = new MedicalConsultationController(new MedicalConsultationView());
        this.planController = new CustomizedPlanController(new CustomizedPlanView());
    }

    public void showMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- MENÚ CONSULTAS Y PLANES ---");
            System.out.println("1. Gestionar Consultas Médicas");
            System.out.println("2. Gestionar Planes Personalizados");
            System.out.println("3. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
                continue;
            }

            switch (option) {
                case 1:
                    consultationController.iniciarMedicalConsultation();
                    break;
                case 2:
                    startPlanManagement();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void startPlanManagement() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- GESTIÓN DE PLANES ---");
            System.out.println("1. Crear nuevo plan");
            System.out.println("2. Ver plan existente");
            System.out.println("3. Listar todos los planes");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
                continue;
            }

            switch (option) {
                case 1:
                    planController.registerPlan();
                    break;
                case 2:
                    int id = new CustomizedPlanView().getPlanId();
                    CustomizedPlan plan = planController.getPlanById(id);
                    if (plan != null) {
                        new CustomizedPlanView().displayPlan(plan);
                    }
                    break;
                case 3:
                    List<CustomizedPlan> plans = planController.getAllPlans();
                    if (plans != null) {
                        new CustomizedPlanView().displayAllPlans(plans);
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}
