package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HealthHistorialDAO {

    BBDD_Connection conexion = new BBDD_Connection();

    public int insertar(HealthHistorial historial) {
        String sql = "INSERT INTO HealthHistorial (weight, pet_id) VALUES (?, ?)";
        int historialId = -1;

        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, historial.getWeight());
            stmt.setInt(2, historial.getPet().getId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    historialId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }

        // Insertar datos en las tablas de relaciones
        insertarAlergias(historialId, historial.getAllergies());
        insertarCondiciones(historialId, historial.getPreExistingConditions());
        insertarProcedimientos(historialId, historial.getProceduresPerformed());
        insertarVacunas(historialId, historial.getVaccines());

        return historialId;
    }

    private void insertarProcedimientos(int historialId, List<ProcedurePerformed> procedures) {
        String insertProcedureSQL = "INSERT INTO ProcedurePerformed (name, description, date, type, preoperativeReport) VALUES (?, ?, ?, ?, ?)";
        String insertRelationSQL = "INSERT INTO HealthHistorial_ProceduresPerformed (healthHistorial_id, procedurePerformed_id) VALUES (?, ?)";

        try (Connection conn = conexion.conectar(); PreparedStatement stmtProcedure = conn.prepareStatement(insertProcedureSQL, Statement.RETURN_GENERATED_KEYS); PreparedStatement stmtRelation = conn.prepareStatement(insertRelationSQL)) {

            for (ProcedurePerformed procedure : procedures) {
                // Insertar el procedimiento si su ID es 0 (nuevo)
                if (procedure.getId() == 0) {
                    stmtProcedure.setString(1, procedure.getName());
                    stmtProcedure.setString(2, procedure.getDescription());
                    stmtProcedure.setDate(3, Date.valueOf(procedure.getDatePerformed()));
                    stmtProcedure.setString(4, procedure.getType());
                    stmtProcedure.setString(5, procedure.getPreoperativeReport());
                    stmtProcedure.executeUpdate();

                    try (ResultSet rs = stmtProcedure.getGeneratedKeys()) {
                        if (rs.next()) {
                            procedure.setId(rs.getInt(1)); // Asignar el ID generado
                        }
                    }
                }

                // Insertar en la tabla de relación
                stmtRelation.setInt(1, historialId);
                stmtRelation.setInt(2, procedure.getId());
                stmtRelation.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }
    }

    private void insertarVacunas(int historialId, List<Vaccines> vaccines) {
        String insertVaccineSQL = "INSERT INTO Vaccine (name, type, manufacturer, pack, applyDate, nextApplyDate, expirationDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertRelationSQL = "INSERT INTO HealthHistorial_Vaccines (healthHistorial_id, vaccine_id) VALUES (?, ?)";

        try (Connection conn = conexion.conectar(); PreparedStatement stmtVaccine = conn.prepareStatement(insertVaccineSQL, Statement.RETURN_GENERATED_KEYS); PreparedStatement stmtRelation = conn.prepareStatement(insertRelationSQL)) {

            for (Vaccines vaccine : vaccines) {
                // Insertar la vacuna si su ID es 0 (nueva)
                if (vaccine.getId() == 0) {
                    stmtVaccine.setString(1, vaccine.getName());
                    stmtVaccine.setString(2, vaccine.getType());
                    stmtVaccine.setString(3, vaccine.getManufacturer());
                    stmtVaccine.setString(4, vaccine.getPack());
                    stmtVaccine.setDate(5, Date.valueOf(vaccine.getApplyDate()));
                    stmtVaccine.setDate(6, Date.valueOf(vaccine.getNextApplyDate()));
                    stmtVaccine.setDate(7, Date.valueOf(vaccine.getExpirationDate()));
                    stmtVaccine.executeUpdate();

                    try (ResultSet rs = stmtVaccine.getGeneratedKeys()) {
                        if (rs.next()) {
                            vaccine.setId(rs.getInt(1)); // Asignar el ID generado
                        }
                    }
                }

                // Insertar en la tabla de relación
                stmtRelation.setInt(1, historialId);
                stmtRelation.setInt(2, vaccine.getId());
                stmtRelation.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }
    }

    private void insertarAlergias(int historialId, List<String> allergies) {
        String sql = "INSERT INTO HealthHistorial_PreExistingConditions (healthHistorial_id, conditions) VALUES (?, ?)";

        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (String allergy : allergies) {
                stmt.setInt(1, historialId);
                stmt.setString(2, allergy);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }
    }

    private void insertarCondiciones(int historialId, List<PreExistingConditions> conditions) {
        String sql = "INSERT INTO HealthHistorial_PreExistingConditions (healthHistorial_id, conditions) VALUES (?, ?)";

        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (PreExistingConditions condition : conditions) {
                stmt.setInt(1, historialId);
                stmt.setString(2, condition.getName());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }
    }

    public List<HealthHistorial> obtenerHistorialesConMascotas() {
        List<HealthHistorial> historiales = new ArrayList<>();

        String sql = "SELECT h.id, h.pet_id, p.name AS pet_name, h.creation_date "
                + "FROM HealthHistorial h "
                + "JOIN Pet p ON h.pet_id = p.id";

        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int petId = rs.getInt("pet_id");
                String petName = rs.getString("pet_name");
                LocalDate creationDate = rs.getDate("creation_date").toLocalDate();

                // Crear el objeto historial con la mascota
                Pet pet = new Pet(petId, petName);  // Suponiendo que tienes un constructor en Pet
                HealthHistorial historial = new HealthHistorial(id, pet, creationDate);
                historiales.add(historial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }

        return historiales;
    }

}
