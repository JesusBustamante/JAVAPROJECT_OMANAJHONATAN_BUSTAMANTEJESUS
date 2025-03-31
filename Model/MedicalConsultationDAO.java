package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalConsultationDAO {

    private CustomizedPlanDAO customizedPlanDAO;

    public MedicalConsultationDAO() {
        this.customizedPlanDAO = new CustomizedPlanDAO();
    }

    private Connection getConnection() throws SQLException {
        return BBDD_Connection.conectar();
    }

    public void createConsultation(MedicalConsultation consultation) throws SQLException {
        String sql = "INSERT INTO MedicalConsultation (date, pet_id, owner_id, reason, status, services, haircut_type, observations, special_conditions, customizedPlan_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, new Timestamp(consultation.getDate().getTime()));
            stmt.setInt(2, consultation.getPetId());
            stmt.setInt(3, consultation.getOwnerId());
            stmt.setString(4, consultation.getReason());
            stmt.setString(5, consultation.getStatus());
            stmt.setString(6, consultation.getServices());
            stmt.setString(7, consultation.getHaircutType());
            stmt.setString(8, consultation.getObservations());
            stmt.setString(9, consultation.getSpecialConditions());

            if (consultation.getCustomizedPlan() != null) {
                stmt.setInt(10, consultation.getCustomizedPlan().getId());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        consultation.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public MedicalConsultation getConsultationById(int id) throws SQLException {
        String sql = "SELECT * FROM MedicalConsultation WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CustomizedPlan plan = customizedPlanDAO.getPlanById(rs.getInt("customizedPlan_id"));

                return new MedicalConsultation(
                        rs.getInt("id"),
                        rs.getTimestamp("date"),
                        rs.getInt("pet_id"),
                        rs.getInt("owner_id"),
                        rs.getString("reason"),
                        rs.getString("status"),
                        rs.getString("services"),
                        rs.getString("haircut_type"),
                        rs.getString("observations"),
                        rs.getString("special_conditions"),
                        plan
                );
            }
        }
        return null;
    }

    public void updateConsultationStatus(int id, String status) throws SQLException {
        String sql = "UPDATE MedicalConsultation SET status = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public List<MedicalConsultation> getAllConsultations() throws SQLException {
        List<MedicalConsultation> consultations = new ArrayList<>();
        String sql = "SELECT * FROM MedicalConsultation";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CustomizedPlan plan = customizedPlanDAO.getPlanById(rs.getInt("customizedPlan_id"));

                consultations.add(new MedicalConsultation(
                        rs.getInt("id"),
                        rs.getTimestamp("date"),
                        rs.getInt("pet_id"),
                        rs.getInt("owner_id"),
                        rs.getString("reason"),
                        rs.getString("status"),
                        rs.getString("services"),
                        rs.getString("haircut_type"),
                        rs.getString("observations"),
                        rs.getString("special_conditions"),
                        plan
                ));
            }
        }
        return consultations;
    }
}
