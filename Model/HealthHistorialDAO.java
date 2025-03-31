package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        PreparedStatement stmtProcedure = null;
        PreparedStatement stmtRelation = null;
        ResultSet generatedKeys = null;

        try {
            conn = BBDD_Connection.conectar();
            conn.setAutoCommit(false); // Iniciar transacción 

            stmtProcedure = conn.prepareStatement(insertProcedureSQL, Statement.RETURN_GENERATED_KEYS);
            stmtRelation = conn.prepareStatement(insertRelationSQL);

            for (ProcedurePerformed procedure : procedures) {
                // Insertar el procedimiento
                stmtProcedure.setString(1, procedure.getName());
                stmtProcedure.setString(2, procedure.getDescription());
                stmtProcedure.setDate(3, Date.valueOf(procedure.getDatePerformed()));
                stmtProcedure.setString(4, procedure.getType());
                stmtProcedure.setString(5, procedure.getPreoperativeReport());
                stmtProcedure.executeUpdate();

                // Obtener el ID generado
                generatedKeys = stmtProcedure.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int procedureId = generatedKeys.getInt(1);

                    // Insertar en la tabla de relación
                    stmtRelation.setInt(1, historialId);
                    stmtRelation.setInt(2, procedureId);
                    stmtRelation.executeUpdate();
                }
            }
            conn.commit(); // Confirmar transacción

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Revertir en caso de error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (stmtProcedure != null) {
                    stmtProcedure.close();
                }
                if (stmtRelation != null) {
                    stmtRelation.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    // Método nuevo para insertar alergias (usar este)
    private void insertarAlergias(int historialId, List<String> alergias) {
        String sql = "INSERT INTO HealthHistorial_Allergies (healthHistorial_id, allergy) VALUES (?, ?)";

        try (Connection conn = BBDD_Connection.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (String alergia : alergias) {
                stmt.setInt(1, historialId);
                stmt.setString(2, alergia.trim()); // Elimina espacios
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar alergias");
            e.printStackTrace();
        }
        // No necesita finally, try-with-resources cierra automáticamente
    }

// Método nuevo para insertar condiciones (usar este)
    private void insertarCondiciones(int historialId, List<PreExistingConditions> condiciones) {
        String sql = "INSERT INTO HealthHistorial_PreExistingConditions (healthHistorial_id, name, description, datePerformed) VALUES (?, ?, ?, ?)";

        try (Connection conn = BBDD_Connection.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (PreExistingConditions condicion : condiciones) {
                stmt.setInt(1, historialId);
                stmt.setString(2, condicion.getName());
                stmt.setString(3, condicion.getDescription());

                if (condicion.getDatePerformed() != null) {
                    stmt.setDate(4, Date.valueOf(condicion.getDatePerformed()));
                } else {
                    stmt.setNull(4, Types.DATE);
                }

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar condiciones preexistentes");
            e.printStackTrace();
        }
    }

    private List<Vaccines> obtenerVacunasPorHistorial(int historialId, Connection conn) {
        List<Vaccines> vaccines = new ArrayList<>();
        String sql = "SELECT v.id, v.name, v.type, v.manufacturer, v.pack, v.applyDate, v.nextApplyDate, v.expirationDate "
                + "FROM Vaccine v "
                + "JOIN HealthHistorial_Vaccines hv ON v.id = hv.vaccine_id "
                + "WHERE hv.healthHistorial_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        }

        return vaccines;
    }

    public List<HealthHistorial> obtenerHistorialesCompletos() {
        List<HealthHistorial> historiales = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = BBDD_Connection.conectar();
            String sql = "SELECT h.id AS historial_id, h.weight, h.pet_id, p.name AS pet_name "
                    + "FROM HealthHistorial h "
                    + "JOIN Pet p ON h.pet_id = p.id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int historialId = rs.getInt("historial_id");
                int petId = rs.getInt("pet_id");
                String petName = rs.getString("pet_name");
                double weight = rs.getDouble("weight");

                Pet pet = new Pet(petId, petName);
                HealthHistorial historial = new HealthHistorial(historialId, pet);
                historial.setWeight(weight);

                // Obtener y asignar todos los datos relacionados
                historial.setVaccines(obtenerVacunasPorHistorial(historialId, conn));
                historial.setProceduresPerformed(obtenerProcedimientosPorHistorial(historialId, conn));
                historial.setAllergies(obtenerAlergiasPorHistorial(historialId, conn));
                historial.setPreExistingConditions(obtenerCondicionesPorHistorial(historialId, conn));

                historiales.add(historial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return historiales;
    }

    // Método para obtener alergias
    private List<String> obtenerAlergiasPorHistorial(int historialId, Connection conn) {
        List<String> alergias = new ArrayList<>();
        String sql = "SELECT allergy FROM HealthHistorial_Allergies WHERE healthHistorial_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, historialId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                alergias.add(rs.getString("allergy"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener alergias para historial " + historialId);
            e.printStackTrace();
        }

        return alergias;
    }

    // Método para obtener condiciones (maneja fechas nulas)
    private List<PreExistingConditions> obtenerCondicionesPorHistorial(int historialId, Connection conn) {
        List<PreExistingConditions> condiciones = new ArrayList<>();
        String sql = "SELECT name, description, datePerformed FROM HealthHistorial_PreExistingConditions WHERE healthHistorial_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, historialId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDate fecha = null;
                Date fechaSQL = rs.getDate("datePerformed");
                if (fechaSQL != null) {
                    fecha = fechaSQL.toLocalDate();
                }

                condiciones.add(new PreExistingConditions(
                        0, // ID temporal
                        rs.getString("name"),
                        rs.getString("description"),
                        fecha
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener condiciones para historial " + historialId);
            e.printStackTrace();
        }

        return condiciones;
    }

    private List<ProcedurePerformed> obtenerProcedimientosPorHistorial(int historialId, Connection conn) {
        List<ProcedurePerformed> procedures = new ArrayList<>();
        String sql = "SELECT pp.id, pp.name, pp.description, pp.date as datePerformed, pp.type, pp.preoperativeReport "
                + "FROM ProcedurePerformed pp "
                + "JOIN HealthHistorial_ProceduresPerformed hp ON pp.id = hp.procedurePerformed_id "
                + "WHERE hp.healthHistorial_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, historialId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProcedurePerformed procedure = new ProcedurePerformed(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("datePerformed").toLocalDate(),
                        rs.getString("type"),
                        rs.getString("preoperativeReport")
                );
                procedures.add(procedure);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener procedimientos para historial " + historialId);
            e.printStackTrace();
        }

        return procedures;
    }

    public void debugProcedimientos(int historialId) {
        String sql = "SELECT hp.healthHistorial_id, hp.procedurePerformed_id, pp.name "
                + "FROM HealthHistorial_ProceduresPerformed hp "
                + "LEFT JOIN ProcedurePerformed pp ON hp.procedurePerformed_id = pp.id "
                + "WHERE hp.healthHistorial_id = ?";

        try (Connection conn = BBDD_Connection.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, historialId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("=== DEBUG PROCEDIMIENTOS ===");
            System.out.println("Para historial ID: " + historialId);

            boolean tieneDatos = false;
            while (rs.next()) {
                tieneDatos = true;
                System.out.println("Procedimiento ID: " + rs.getInt("procedurePerformed_id")
                        + " - Nombre: " + rs.getString("name"));
            }

            if (!tieneDatos) {
                System.out.println("No se encontraron procedimientos relacionados");
            }

        } catch (SQLException e) {
            System.err.println("Error en debugProcedimientos: " + e.getMessage());
        }
    }

    public void debugAllProcedures() {
        String sql = "SELECT * FROM ProcedurePerformed";

        try (Connection conn = BBDD_Connection.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("=== TODOS LOS PROCEDIMIENTOS EN BD ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id")
                        + ", Nombre: " + rs.getString("name")
                        + ", Tipo: " + rs.getString("type")
                        + ", Fecha: " + rs.getDate("date"));
            }
        } catch (SQLException e) {
            System.err.println("Error en debugAllProcedures: " + e.getMessage());
        }
    }
}
