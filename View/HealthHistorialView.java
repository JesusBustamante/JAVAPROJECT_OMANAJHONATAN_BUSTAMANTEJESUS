package View;

import Model.HealthHistorial;
import Model.ProcedurePerformed;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HealthHistorialView {

    private Scanner scanner;

    public HealthHistorialView() {
        this.scanner = new Scanner(System.in);
    }

    public int solicitarPetId() {
        System.out.print("Ingrese el ID de la mascota: ");
        return scanner.nextInt();
    }

    public double solicitarPeso() {
        System.out.print("Ingrese el peso de la mascota (kg): ");
        return scanner.nextDouble();
    }

    public List<String> solicitarAlergias() {
        scanner.nextLine(); // Limpiar buffer
        List<String> allergies = new ArrayList<>();
        while (true) {
            System.out.print("Ingrese una alergia (o escriba 'fin' para terminar): ");
            String allergy = scanner.nextLine().trim();
            if (allergy.equalsIgnoreCase("fin")) {
                break;
            }
            allergies.add(allergy);
        }
        return allergies;
    }

    public List<String> solicitarCondiciones() {
        List<String> conditions = new ArrayList<>();
        while (true) {
            System.out.print("Ingrese una condición preexistente (o escriba 'fin' para terminar): ");
            String condition = scanner.nextLine().trim();
            if (condition.equalsIgnoreCase("fin")) {
                break;
            }
            conditions.add(condition);
        }
        return conditions;
    }

    public List<ProcedurePerformed> solicitarProcedimientos() {
        List<ProcedurePerformed> procedures = new ArrayList<>();

        while (true) {
            String name;
            String type;

            // Primero pedimos el nombre del procedimiento
            System.out.print("Ingrese el nombre del procedimiento (o escriba 'fin' para terminar): ");
            name = scanner.nextLine().trim();

            // Si el usuario intenta salir escribiendo "fin"
            if (name.equalsIgnoreCase("fin")) {
                // Si no ha ingresado ningún procedimiento antes, no lo dejamos salir
                if (procedures.isEmpty()) {
                    System.out.println("⚠ Debe registrar al menos un procedimiento antes de salir.");
                    continue; // Volver a pedir el nombre
                } else {
                    break; // Si ya hay procedimientos registrados, salir del bucle
                }
            }

            System.out.print("Ingrese la descripción del procedimiento (puede dejarlo vacío): ");
            String description = scanner.nextLine().trim();

            // Pedimos el tipo (NO puede ser vacío)
            do {
                System.out.print("Ingrese el tipo de procedimiento (Obligatorio): ");
                type = scanner.nextLine().trim();
                if (type.isEmpty()) {
                    System.out.println("⚠ El tipo no puede estar vacío. Inténtelo de nuevo.");
                }
            } while (type.isEmpty()); // Se repetirá hasta que se ingrese un valor válido.

            System.out.print("Ingrese el reporte preoperatorio (puede dejarlo vacío, escriba 'N/A' si no aplica): ");
            String preoperativeReport = scanner.nextLine().trim();

            // Agregar el procedimiento con todos los valores
            procedures.add(new ProcedurePerformed(0, name, description, LocalDate.now(), type, preoperativeReport));
        }

        return procedures;
    }

    public List<String> solicitarVacunas() {
        List<String> vaccines = new ArrayList<>();
        while (true) {
            System.out.print("Ingrese una vacuna aplicada (o escriba 'fin' para terminar): ");
            String vaccine = scanner.nextLine().trim();
            if (vaccine.equalsIgnoreCase("fin")) {
                break;
            }
            vaccines.add(vaccine);
        }
        return vaccines;
    }

    public String solicitarTipoVacuna(String vaccineName) {
        System.out.print("Ingrese el tipo de la vacuna '" + vaccineName + "': ");
        return scanner.nextLine().trim();
    }

    public String solicitarFabricanteVacuna(String vaccineName) {
        System.out.print("Ingrese el fabricante de la vacuna '" + vaccineName + "': ");
        return scanner.nextLine().trim();
    }

    public String solicitarLoteVacuna(String vaccineName) {
        System.out.print("Ingrese el lote de la vacuna '" + vaccineName + "': ");
        return scanner.nextLine().trim();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarHistoriales(List<HealthHistorial> historiales) {
        if (historiales.isEmpty()) {
            System.out.println("🚫 No hay historiales médicos registrados.");
            return;
        }

        System.out.println("\n📋 Lista de Historiales Médicos:");
        for (HealthHistorial historial : historiales) {
            System.out.println("🔹 ID Historial: " + historial.getId());
            System.out.println("🐾 Mascota: " + historial.getPet().getName() + " (ID: " + historial.getPet().getId() + ")");

            if (historial.getProceduresPerformed().isEmpty()) {
                System.out.println("   📌 No hay procedimientos registrados.");
            } else {
                System.out.println("   🏥 Procedimientos:");
                for (ProcedurePerformed p : historial.getProceduresPerformed()) {
                    System.out.println("   - 🩺 " + p.getName() + " (" + p.getType() + "), realizado el " + p.getDatePerformed());
                }
            }
            System.out.println("-----------------------------------");
        }
    }

}
