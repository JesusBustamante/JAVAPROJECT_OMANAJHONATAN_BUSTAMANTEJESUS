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
        List<String> vaccineNames = historialView.solicitarVacunas(); 

        // Convertir condiciones a objetos
        List<PreExistingConditions> conditionObjects = new ArrayList<>();
        for (String c : conditions) {
            conditionObjects.add(new PreExistingConditions(0, c, "Descripción", LocalDate.now()));
        }

        List<Vaccines> vaccineObjects = new ArrayList<>();
        for (String vaccineName : vaccineNames) {
            String type = historialView.solicitarTipoVacuna(vaccineName);
            String manufacturer = historialView.solicitarFabricanteVacuna(vaccineName);
            String lot = historialView.solicitarLoteVacuna(vaccineName);
            LocalDate applyDate = LocalDate.now();
            LocalDate nextApplyDate = applyDate.plusMonths(6);
            LocalDate expirationDate = applyDate.plusYears(2);

            vaccineObjects.add(new Vaccines(0, vaccineName, type, manufacturer, lot, applyDate, nextApplyDate, expirationDate));
        }

        // Crear historial médico y guardarlo en la base de datos
        HealthHistorial historial = new HealthHistorial(0, pet, weight, null, vaccineObjects, allergies, conditionObjects, new ArrayList<>());
        historialDAO.insertar(historial);

        historialView.mostrarMensaje("✔ Historial médico registrado para la mascota " + pet.getName() + " de " + pet.getOwner().getFull_name());
        historialView.mostrarHistoriales(HealthHistorial.List<HealthHistorial>historiales);
    }

}
