package View;

import Model.Gender;
import Model.Owner;
import Model.Pet;
import java.time.LocalDate;
import java.util.Scanner;

public class PetView {

    private Scanner scanner;

    public PetView() {
        this.scanner = new Scanner(System.in);
    }

    public Pet agregarPet(Owner owner) {
        System.out.println("\nDatos para nueva mascota:\n");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Especie: ");
        String species = scanner.nextLine();
        System.out.print("Raza: ");
        String race = scanner.nextLine();

        String ageInput;
        int age = 0;
        String ageUnit = ""; 

        while (true) {
            System.out.print("Ingrese la edad (Ejemplo: 3 meses o 2 años): ");
            ageInput = scanner.nextLine().trim().toLowerCase();

            if (ageInput.matches("\\d+\\s*(mes|meses|año|años)")) {
                String[] parts = ageInput.split("\\s+");
                age = Integer.parseInt(parts[0]);
                ageUnit = parts[1];
                break;
            } else {
                System.out.println("Formato incorrecto. Use 'X mes', 'X meses', 'X año' o 'X años'.");
            }
        }

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        String birthDate = scanner.nextLine();
        System.out.print("Género (MALE/FEMALE): ");
        String genderInput = scanner.nextLine().toUpperCase(); // Convertir a mayúsculas
        Gender gender = Gender.valueOf(genderInput); // Convertir String a enum
        System.out.print("Microchip: ");
        String microchip = scanner.nextLine();

        boolean tattoo = false;
        while (true) {
            System.out.print("¿Tiene tatuaje? (Sí/No): ");
            String tattooInput = scanner.nextLine().trim().toLowerCase();
            if (tattooInput.equals("sí")) {
                tattoo = true;
                break;
            } else if (tattooInput.equals("no")) {
                tattoo = false;
                break;
            } else {
                System.out.println("Entrada no válida. Por favor, escriba 'Sí' o 'No'.");
            }
        }

        System.out.print("Foto (URL): ");
        String photo = scanner.nextLine();

        return new Pet(name, species, race, age, LocalDate.parse(birthDate), gender, microchip, tattoo, photo, owner);
    }

}
