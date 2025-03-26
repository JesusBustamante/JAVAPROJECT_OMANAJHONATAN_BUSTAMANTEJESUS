package Controller;

import Model.*;
import View.HealthHistorialView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HealthHistorialController {

    private HealthHistorialDAO historialDAO;
    private PetDAO petDAO;
    private HealthHistorialView historialView;

    public HealthHistorialController(HealthHistorialDAO historialDAO, PetDAO petDAO, HealthHistorialView historialView) {
        this.historialDAO = historialDAO;
        this.petDAO = petDAO;
        this.historialView = historialView;
    }

    public void registrarHistorialMedico() {

        int petId = historialView.solicitarPetId();
        Pet pet = petDAO.buscarPorId(petId);

        if (pet == null) {
            historialView.mostrarMensaje("❌ No existe una mascota con ese ID.");
            return;
        }

        double weight = historialView.solicitarPeso();
        List<String> allergies = historialView.solicitarAlergias();
        List<String> conditions = historialView.solicitarCondiciones();
        List<String> procedures = historialView.solicitarProcedimientos();
        List<String> vaccines = historialView.solicitarVacunas();

        // Convertir condiciones, procedimientos y vacunas a objetos
        List<PreExistingConditions> conditionObjects = new ArrayList<>();
        for (String c : conditions) {
            conditionObjects.add(new PreExistingConditions(0, c, "Descripción", LocalDate.now()));
        }

        List<ProcedurePerformed> procedureObjects = new ArrayList<>();
        for (String p : procedures) {
            procedureObjects.add(new ProcedurePerformed(
                    0, // ID
                    p, // Nombre
                    "Descripción del procedimiento", // Descripción
                    LocalDate.now(), // Fecha realizada
                    "Tipo de procedimiento", // Tipo
                    "Reporte preoperatorio" // Reporte preoperatorio
            ));
        }

        List<Vaccines> vaccineObjects = new ArrayList<>();
        for (String v : vaccines) {
            vaccineObjects.add(new Vaccines(0, v, "Tipo", "Fabricante", "Lote", LocalDate.now(), LocalDate.now().plusMonths(6), LocalDate.now().plusYears(2)));
        }

        // Crear historial y guardarlo en la base de datos
        HealthHistorial historial = new HealthHistorial(0, pet, weight, null, vaccineObjects, allergies, conditionObjects, procedureObjects);
        historialDAO.insertar(historial);

        historialView.mostrarMensaje("✔ Historial médico registrado para la mascota " + pet.getName() + " de " + pet.getOwner().getFull_name());
    }
}
