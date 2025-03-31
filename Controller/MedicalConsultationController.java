package Controller;

import Model.MedicalConsultation;
import Model.MedicalConsultationDAO;
import View.MedicalConsultationView;
import java.sql.Connection;
import java.util.List;

public class MedicalConsultationController {
    private MedicalConsultationDAO medicalConsultationDAO;
    private MedicalConsultationView medicalConsultationView;

    public MedicalConsultationController(MedicalConsultationView medicalConsultationView) {
        this.medicalConsultationDAO = new MedicalConsultationDAO();
        this.medicalConsultationView = medicalConsultationView;
    }

    public void iniciarMedicalConsultation() {
        boolean salir = false;
        while (!salir) {
            medicalConsultationView.mostrarMenuConsultas();
            int opcion = medicalConsultationView.leerOpcion();
            
            switch (opcion) {
                case 1:
                    crearConsulta();
                    break;
                case 2:
                    listarConsultas();
                    break;
                case 3:
                    actualizarEstadoConsulta();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    medicalConsultationView.mostrarMensaje("Opción no válida");
            }
        }
    }

    private void crearConsulta() {
        MedicalConsultation consulta = medicalConsultationView.getConsultationDetails();
        try {
            medicalConsultationDAO.createConsultation(consulta);
            medicalConsultationView.mostrarMensaje("Consulta creada exitosamente");
        } catch (Exception e) {
            medicalConsultationView.mostrarMensaje("Error al crear la consulta: " + e.getMessage());
        }
    }

    private void listarConsultas() {
        try {
            List<MedicalConsultation> consultas = medicalConsultationDAO.getAllConsultations();
            medicalConsultationView.mostrarConsultas(consultas);
        } catch (Exception e) {
            medicalConsultationView.mostrarMensaje("Error al listar consultas: " + e.getMessage());
        }
    }

    private void actualizarEstadoConsulta() {
        int id = medicalConsultationView.getConsultationId();
        String nuevoEstado = medicalConsultationView.getNewStatus();
        try {
            medicalConsultationDAO.updateConsultationStatus(id, nuevoEstado);
            medicalConsultationView.mostrarMensaje("Estado actualizado exitosamente");
        } catch (Exception e) {
            medicalConsultationView.mostrarMensaje("Error al actualizar el estado: " + e.getMessage());
        }
    }
}