package View;

import Model.Owner;
import Model.Pet;
import java.util.List;
import java.util.Scanner;

public class OwnerView {

    private Scanner scanner;

    public OwnerView() {
        this.scanner = new Scanner(System.in);
    }

    public Owner agregarOwner() {
        System.out.println("\nDatos para nuevo dueño:\n");
        System.out.print("Documento de identidad: ");

        int personalId;
        while (true) {
            System.out.print("Ingrese su Cédula (solo números, máx. 10 dígitos): ");
            String input = scanner.nextLine().trim();

            if (input.matches("\\d+")) { // Verifica que solo sean números
                try {
                    personalId = Integer.parseInt(input); // Convertir a int
                    System.out.println("✔ Cédula válida.");
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("❌ Error: Número demasiado grande. Ingrese un valor menor o igual a 2,147,483,647.");
                }
            } else {
                System.out.println("❌ Error: La Cédula debe contener solo números.");
            }
        }

        String NIT;
        while (true) {
            System.out.print("Ingrese su NIT (formato '123456789-0'): ");
            NIT = scanner.nextLine().trim();

            if (NIT.matches("\\d{7,10}-\\d")) {
                System.out.println("✔ NIT válido.");
                break;
            } else {
                System.out.println("❌ Error: El NIT debe tener el formato correcto (Ejemplo: 123456789-0).");
            }
        }

        System.out.print("Nombre completo: ");
        String fullName = scanner.nextLine();
        System.out.print("Dirección: ");
        String address = scanner.nextLine();
        System.out.print("Teléfono: ");
        String cellphone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contacto de emergencia: ");
        String emergencyContact = scanner.nextLine();

        return new Owner(personalId, NIT, fullName, address, cellphone, email, emergencyContact);
    }

    public void mostrarOwners(List<Owner> owners) {
        System.out.println("\nLista de dueños:\n");
        for (Owner owner : owners) {
            System.out.println("Dueño: " + owner.getId() + " - " + owner.getFull_name());

            List<Pet> pets = owner.getPets();

            if (pets.isEmpty()) {
                System.out.println("   No tiene mascotas registradas.");
            } else {
                for (Pet pet : pets) {
                    System.out.println("   Mascota: " + pet.getId() + " - " + pet.getName() + ", Especie: " + pet.getSpecies());
                }
            }
        }
    }
}
