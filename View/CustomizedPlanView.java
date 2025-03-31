package View;

import Model.CustomizedPlan;
import Model.Pet;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CustomizedPlanView {

    private Scanner scanner;

    public CustomizedPlanView() {
        scanner = new Scanner(System.in);
    }

    public CustomizedPlan getPlanDetails() {
        System.out.println("\n--- Crear Plan Personalizado ---");

        System.out.print("Nombre del plan: ");
        String name = scanner.nextLine();

        System.out.print("Descripción: ");
        String description = scanner.nextLine();

        System.out.print("Raza específica: ");
        String race = scanner.nextLine();

        int behavior;
        while (true) {
            System.out.print("Nivel de comportamiento (1-5): ");
            try {
                behavior = Integer.parseInt(scanner.nextLine());
                if (behavior >= 1 && behavior <= 5) {
                    break;
                }
                System.out.println("Error: Ingrese un valor entre 1 y 5.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }

        System.out.print("Recomendaciones (separadas por comas): ");
        List<String> recommendations = Arrays.asList(scanner.nextLine().split(","));

        int petId;
        while (true) {
            System.out.print("ID de la mascota: ");
            try {
                petId = Integer.parseInt(scanner.nextLine());
                if (petId > 0) {
                    break;
                }
                System.out.println("Error: El ID debe ser positivo.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }

        Pet pet = new Pet(petId);
        return new CustomizedPlan(0, name, description, race, behavior, recommendations, pet);
    }

    // Método para mostrar mensaje de éxito
    public void displaySuccessMessage(String message) {
        System.out.println("\nÉxito: " + message);
    }

    // Método para mostrar mensaje de error
    public void displayErrorMessage(String message) {
        System.out.println("\nError: " + message);
    }

    public void displayPlan(CustomizedPlan plan) {
        System.out.println("\n--- Detalles del Plan ---");
        System.out.println("ID: " + plan.getId());
        System.out.println("Nombre: " + plan.getName());
        System.out.println("Descripción: " + plan.getDescription());
        System.out.println("Raza: " + plan.getRace());
        System.out.println("Nivel de Comportamiento: " + plan.getBehavior());
        System.out.println("Recomendaciones: " + String.join(", ", plan.getRecommendations()));
        if (plan.getPet() != null) {
            System.out.println("Mascota ID: " + plan.getPet().getId());
        }
    }

    public int getPlanId() {
        System.out.print("\nIngrese ID del plan: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void displayAllPlans(List<CustomizedPlan> plans) {
        System.out.println("\n--- Todos los Planes Personalizados ---");
        if (plans == null || plans.isEmpty()) {
            System.out.println("No hay planes registrados.");
        } else {
            for (CustomizedPlan plan : plans) {
                displayPlan(plan);
                System.out.println("-----------------------------");
            }
        }
    }
}
