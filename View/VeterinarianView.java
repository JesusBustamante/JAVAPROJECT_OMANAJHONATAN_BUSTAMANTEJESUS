package View;

import java.util.List;
import java.util.Scanner;
import Model.Veterinarian;

public class VeterinarianView {
    private Scanner sc;

    public VeterinarianView() {
        this.sc = new Scanner(System.in);
    }

    public Veterinarian agregarVeterinario() {
        System.out.println("\n📌 Registro de nuevo veterinario:");

        // Validar nombre
        String name;
        do {
            System.out.print("🔹 Nombre: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("⚠ El nombre no puede estar vacío.");
            }
        } while (name.isEmpty());

        // Validar especialización
        String specialization;
        do {
            System.out.print("🔹 Especialización: ");
            specialization = sc.nextLine().trim();
            if (specialization.isEmpty()) {
                System.out.println("⚠ La especialización no puede estar vacía.");
            }
        } while (specialization.isEmpty());

        return new Veterinarian(name, specialization);
    }

    public void mostrarVeterinarios(List<Veterinarian> veterinarians) {
        if (veterinarians.isEmpty()) {
            System.out.println("\n🚫 No hay veterinarios registrados.");
        } else {
            System.out.println("\n📋 Veterinarios Registrados:");
            for (Veterinarian v : veterinarians) {
                System.out.println("🔹 ID: " + v.getId() + " | Dr. " + v.getName() + " - Especialización: " + v.getSpecialization());
            }
        }
    }
}
