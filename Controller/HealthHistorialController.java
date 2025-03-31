package Controller;

import Model.*;
import View.HealthHistorialView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HealthHistorialController {

    static Scanner sc = new Scanner(System.in);

    private HealthHistorialDAO historialDAO;
    private PetDAO petDAO;
    private HealthHistorialView historialView;

    public HealthHistorialController(HealthHistorialDAO historialDAO, PetDAO petDAO, HealthHistorialView historialView) {
        this.historialDAO = historialDAO;
        this.petDAO = petDAO;
        this.historialView = historialView;
    }

    public void iniciarHealthHistorial() {
        while (true) {
            System.out.println("\n=== MÓDULO DE HISTORIALES MÉDICOS ===");
            System.out.println("1. Registar historial médico");
            System.out.println("2️. Listar historiales médicos");
            System.out.println("3️. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarHistorialMedico();
                    break;
                case 2:
                    ShowVeterinarians();
                    break;
                case 3:
                    System.out.println("🔙 Volviendo al menú principal...");
                    return;
                default:
                    System.out.println("❌ Opción no válida. Intente de nuevo.");
            }
        }
    }

    public void registrarHistorialMedico() {
        try {
            // 1. Obtener ID de mascota
            int petId = historialView.solicitarPetId();
            Pet pet = petDAO.buscarPorId(petId);

            if (pet == null) {
                historialView.mostrarMensaje("❌ No existe una mascota con ese ID.");
                return;
            }

            // 2. Recoger datos básicos
            double weight = historialView.solicitarPeso();
            List<String> allergies = historialView.solicitarAlergias();

            // 3. Recoger condiciones preexistentes con fechas opcionales
            List<PreExistingConditions> conditionObjects = new ArrayList<>();
            List<String> condicionesInput = historialView.solicitarCondiciones();
            for (String condicionNombre : condicionesInput) {
                String descripcion = historialView.solicitarDescripcionCondicion(condicionNombre);
                LocalDate fecha = historialView.solicitarFechaCondicion(condicionNombre);

                conditionObjects.add(new PreExistingConditions(
                        0,
                        condicionNombre,
                        descripcion,
                        fecha
                ));
            }

            // 4. Recoger vacunas
            List<Vaccines> vaccineObjects = new ArrayList<>();
            List<String> vaccineNames = historialView.solicitarVacunas();
            for (String vaccineName : vaccineNames) {
                String type = historialView.solicitarTipoVacuna(vaccineName);
                String manufacturer = historialView.solicitarFabricanteVacuna(vaccineName);
                String lot = historialView.solicitarLoteVacuna(vaccineName);
                LocalDate applyDate = historialView.solicitarFechaAplicacionVacuna(vaccineName);

                // Calcular fechas derivadas
                LocalDate nextApplyDate = applyDate.plusMonths(6);
                LocalDate expirationDate = applyDate.plusYears(2);

                vaccineObjects.add(new Vaccines(
                        0, vaccineName, type, manufacturer, lot,
                        applyDate, nextApplyDate, expirationDate
                ));
            }

            // 5. RECOGER PROCEDIMIENTOS (NUEVO)
            List<ProcedurePerformed> procedimientos = historialView.solicitarProcedimientos();

            // 6. Crear y guardar historial (ahora incluyendo procedimientos)
            HealthHistorial historial = new HealthHistorial(
                    0, pet, weight, null,
                    vaccineObjects, allergies,
                    conditionObjects, procedimientos // Ahora pasamos la lista de procedimientos
            );

            int historialId = historialDAO.insertar(historial);

            System.out.println("\n=== INICIO DE DEPURACIÓN ===");
            historialDAO.debugProcedimientos(historialId);
            historialDAO.debugAllProcedures();
            System.out.println("=== FIN DE DEPURACIÓN ===\n");

            if (historialId > 0) {
                historialView.mostrarMensaje("✔ Historial médico registrado exitosamente para " + pet.getName());
                historialView.mostrarResumenHistorial(historial);
            } else {
                historialView.mostrarMensaje("❌ Error al registrar el historial médico");
            }

        } catch (Exception e) {
            historialView.mostrarMensaje("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void ShowVeterinarians() {
        try {
            List<HealthHistorial> historiales = historialDAO.obtenerHistorialesCompletos();
            historialView.mostrarHistoriales(historiales);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al mostrar los historiales: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
