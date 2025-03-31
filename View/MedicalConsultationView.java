package View;

import Model.CustomizedPlan;
import Model.MedicalConsultation;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MedicalConsultationView {

    private Scanner scanner;

    public MedicalConsultationView() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenuConsultas() {
        System.out.println("\n=== MENÚ DE CONSULTAS MÉDICAS ===");
        System.out.println("1. Crear nueva consulta");
        System.out.println("2. Listar consultas");
        System.out.println("3. Actualizar estado de consulta");
        System.out.println("4. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    public int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public MedicalConsultation getConsultationDetails() {
        System.out.println("\n--- NUEVA CONSULTA MÉDICA ---");

        System.out.print("ID de la mascota: ");
        int petId = Integer.parseInt(scanner.nextLine());

        System.out.print("ID del dueño: ");
        int ownerId = Integer.parseInt(scanner.nextLine());

        System.out.print("Razón de la consulta: ");
        String reason = scanner.nextLine();

        System.out.print("Servicios requeridos (separados por coma): ");
        String services = scanner.nextLine();

        System.out.print("Tipo de corte (si aplica): ");
        String haircutType = scanner.nextLine();

        System.out.print("Observaciones: ");
        String observations = scanner.nextLine();

        System.out.print("Condiciones especiales: ");
        String specialConditions = scanner.nextLine();

        System.out.print("ID del plan personalizado (0 si no aplica): ");
        int planId = Integer.parseInt(scanner.nextLine());

        CustomizedPlan plan = null;
        if (planId > 0) {
            plan = new CustomizedPlan(planId); 
        }

        return new MedicalConsultation(
                0, 
                new Date(),
                petId,
                ownerId,
                reason,
                "Programada",
                services,
                haircutType,
                observations,
                specialConditions,
                plan 
        );
    }

    public void mostrarConsultas(List<MedicalConsultation> consultas) {
        System.out.println("\n--- LISTADO DE CONSULTAS ---");
        if (consultas.isEmpty()) {
            System.out.println("No hay consultas registradas.");
        } else {
            for (MedicalConsultation consulta : consultas) {
                mostrarDetallesConsulta(consulta);
                System.out.println("------------------------------");
            }
        }
    }

    public void mostrarDetallesConsulta(MedicalConsultation consulta) {
        System.out.println("ID: " + consulta.getId());
        System.out.println("Fecha: " + consulta.getDate());
        System.out.println("Mascota ID: " + consulta.getPetId());
        System.out.println("Dueño ID: " + consulta.getOwnerId());
        System.out.println("Razón: " + consulta.getReason());
        System.out.println("Estado: " + consulta.getStatus());
        System.out.println("Servicios: " + consulta.getServices());
        System.out.println("Tipo corte: " + consulta.getHaircutType());
        System.out.println("Observaciones: " + consulta.getObservations());
    }

    public int getConsultationId() {
        System.out.print("\nIngrese el ID de la consulta: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String getNewStatus() {
        System.out.println("\nEstados disponibles:");
        System.out.println("1. Programada");
        System.out.println("2. En proceso");
        System.out.println("3. Finalizada");
        System.out.println("4. Cancelada");
        System.out.print("Seleccione el nuevo estado (1-4): ");

        int opcion = Integer.parseInt(scanner.nextLine());
        switch (opcion) {
            case 1:
                return "Programada";
            case 2:
                return "En proceso";
            case 3:
                return "Finalizada";
            case 4:
                return "Cancelada";
            default:
                return "Programada";
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensajeError) {
        System.err.println("Error: " + mensajeError);
    }
}
