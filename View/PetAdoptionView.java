package View;

import Model.Adopter;
import Model.PetAdoption;
import java.util.List;
import java.util.Scanner;

public class PetAdoptionView {
    private Scanner scanner;

    public PetAdoptionView() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("\n--- Sistema de Adopción de Mascotas ---");
        System.out.println("1. Agregar mascota");
        System.out.println("2. Listar mascotas");
        System.out.println("3. Registrar adoptante");
        System.out.println("4. Adoptar mascota");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public String getInputString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public int getInputInt(String message) {
        System.out.println(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Ingrese un número válido:");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return input;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showPets(List<PetAdoption> pets) {
        if (pets.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
        } else {
            System.out.println("Lista de mascotas disponibles para adopción:");
            for (PetAdoption pet : pets) {
                if (!pet.isAdopted()) {
                    System.out.println("ID: " + pet.getId() + " | Nombre: " + pet.getName() + " | Especie: " + pet.getSpecies() + " | Edad: " + pet.getAge());
                }
            }
        }
    }

    public Adopter getAdopterDetails() {
        String personalId = getInputString("Ingrese su identificación personal:");
        String fullName = getInputString("Ingrese su nombre completo:");
        String address = getInputString("Ingrese su dirección:");
        String cellphone = getInputString("Ingrese su número de celular:");
        String email = getInputString("Ingrese su email:");
        String reference = getInputString("Ingrese una referencia personal:");
        
        return new Adopter(personalId, fullName, address, cellphone, email, reference);
    }

    public int getPetIdForAdoption() {
        return getInputInt("Ingrese el ID de la mascota que desea adoptar:");
    }

    public int getAdopterId() {
        return getInputInt("Ingrese su ID de adoptante para confirmar la adopción:");
    }

    public void confirmAdoptionSuccess() {
        System.out.println("¡Adopción registrada con éxito! La mascota ha sido adoptada.");
    }

    public void confirmAdoptionFailure() {
        System.out.println("No se pudo completar la adopción. Verifique los datos e intente nuevamente.");
    }
}
