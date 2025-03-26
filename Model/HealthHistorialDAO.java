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

        Connection conn = null;

        try {
            conn = BBDD_Connection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setDouble(1, historial.getWeight());
            stmt.setInt(2, historial.getPet().getId());
            stmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    historialId = rs.getInt(1);
                }
            }

            // Insertar datos en las tablas de relaciones
            insertarVacunas(historialId, historial.getVaccines());
            insertarAlergias(historialId, historial.getAllergies());
            insertarCondiciones(historialId, historial.getPreExistingConditions());
            insertarProcedimientos(historialId, historial.getProceduresPerformed());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }

        return historialId;
    }

    private void insertarProcedimientos(int historialId, List<ProcedurePerformed> procedures) {
        String insertProcedureSQL = "INSERT INTO ProcedurePerformed (name, description, date, type, preoperativeReport) VALUES (?, ?, ?, ?, ?)";
        String insertRelationSQL = "INSERT INTO HealthHistorial_ProceduresPerformed (healthHistorial_id, procedurePerformed_id) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = BBDD_Connection.conectar();
            PreparedStatement stmtProcedure = conn.prepareStatement(insertProcedureSQL, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmtRelation = conn.prepareStatement(insertRelationSQL);

            for (ProcedurePerformed procedure : procedures) {
                // Insertar el procedimiento si su ID es 0 (nuevo)
                if (procedure.getId() == 0) {
                    stmtProcedure.setString(1, procedure.getName());
                    stmtProcedure.setString(2, procedure.getDescription());
                    stmtProcedure.setDate(3, Date.valueOf(procedure.getDatePerformed()));
                    stmtProcedure.setString(4, procedure.getType());
                    stmtProcedure.setString(5, procedure.getPreoperativeReport());
                    stmtProcedure.executeUpdate();

                    // Obtener el ID generado para asignarlo correctamente
                    try (ResultSet rs = stmtProcedure.getGeneratedKeys()) {
                        if (rs.next()) {
                            procedure.setId(rs.getInt(1));
                        }
                    }
                }

                // Ahora que el procedimiento tiene un ID válido, lo insertamos en la tabla de relación
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

        Connection conn = null;

        try {
            conn = BBDD_Connection.conectar();
            PreparedStatement stmtVaccine = conn.prepareStatement(insertVaccineSQL, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmtRelation = conn.prepareStatement(insertRelationSQL);

            for (Vaccines vaccine : vaccines) {
                // Insertar la vacuna en la tabla Vaccine
                stmtVaccine.setString(1, vaccine.getName());
                stmtVaccine.setString(2, vaccine.getType());
                stmtVaccine.setString(3, vaccine.getManufacturer());
                stmtVaccine.setString(4, vaccine.getPack());
                stmtVaccine.setDate(5, Date.valueOf(vaccine.getApplyDate()));
                stmtVaccine.setDate(6, Date.valueOf(vaccine.getNextApplyDate()));
                stmtVaccine.setDate(7, Date.valueOf(vaccine.getExpirationDate()));
                stmtVaccine.executeUpdate();

                // Obtener el ID generado de la vacuna
                try (ResultSet rs = stmtVaccine.getGeneratedKeys()) {
                    if (rs.next()) {
                        vaccine.setId(rs.getInt(1));
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

    private List<Vaccines> obtenerVacunasPorHistorial(int historialId) {
        List<Vaccines> vaccines = new ArrayList<>();
        String sql = "SELECT v.id, v.name, v.type, v.manufacturer, v.pack, v.applyDate, v.nextApplyDate, v.expirationDate "
                + "FROM Vaccine v "
                + "JOIN HealthHistorial_Vaccines hv ON v.id = hv.vaccine_id "
                + "WHERE hv.healthHistorial_id = ?";

        Connection conn = null;

        try {
            conn = BBDD_Connection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, historialId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vaccines vaccine = new Vaccines(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("manufacturer"),
                        rs.getString("pack"),
                        rs.getDate("applyDate").toLocalDate(),
                        rs.getDate("nextApplyDate").toLocalDate(),
                        rs.getDate("expirationDate").toLocalDate()
                );
                vaccines.add(vaccine);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }

        return vaccines;
    }

    public List<HealthHistorial> obtenerHistorialesConMascotas() {
        List<HealthHistorial> historiales = new ArrayList<>();
        String sql = "SELECT h.id, h.pet_id, p.name AS pet_name "
                + "FROM HealthHistorial h "
                + "JOIN Pet p ON h.pet_id = p.id";

        Connection conn = null;

        try {
            conn = BBDD_Connection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int historialId = rs.getInt("id");
                int petId = rs.getInt("pet_id");
                String petName = rs.getString("pet_name");

                // Crear el objeto mascota
                Pet pet = new Pet(petId, petName);

                // Obtener procedimientos y vacunas de este historial
                List<ProcedurePerformed> procedures = obtenerProcedimientosPorHistorial(historialId);
                List<Vaccines> vaccines = obtenerVacunasPorHistorial(historialId);

                // Crear historial con las vacunas y procedimientos
                HealthHistorial historial = new HealthHistorial(historialId, pet, vaccines, procedures);
                historiales.add(historial);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }

        return historiales;
    }

    private List<ProcedurePerformed> obtenerProcedimientosPorHistorial(int historialId) {
        List<ProcedurePerformed> procedures = new ArrayList<>();
        String sql = "SELECT pp.id, pp.name, pp.description, pp.date, pp.type, pp.preoperativeReport "
                + "FROM ProcedurePerformed pp "
                + "JOIN HealthHistorial_ProceduresPerformed hp ON pp.id = hp.procedurePerformed_id "
                + "WHERE hp.healthHistorial_id = ?";

        Connection conn = null;

        try {
            conn = BBDD_Connection.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, historialId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProcedurePerformed procedure = new ProcedurePerformed(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("type"),
                        rs.getString("preoperativeReport")
                );
                procedures.add(procedure);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }

        return procedures;
    }

}
