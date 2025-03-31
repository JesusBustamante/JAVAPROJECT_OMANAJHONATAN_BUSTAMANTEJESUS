package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomizedPlanDAO {

    private Connection connection;
    private PetDAO petDAO;

    public CustomizedPlanDAO() {
        this.petDAO = new PetDAO();
    }

    private Connection getConnection() throws SQLException {
        return BBDD_Connection.conectar();
    }

    public void createCustomizedPlan(CustomizedPlan plan) throws SQLException {
        // Validar que el Pet exista
        Pet pet = petDAO.buscarPorId(plan.getPet().getId());
        if (pet == null) {
            throw new SQLException("La mascota con ID " + plan.getPet().getId() + " no existe.");
        }

        String sql = "INSERT INTO CustomizedPlan (name, description, race, behavior, pet_id, recommendations) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, plan.getName());
            stmt.setString(2, plan.getDescription());
            stmt.setString(3, plan.getRace());
            stmt.setInt(4, plan.getBehavior());
            stmt.setInt(5, plan.getPet().getId());
            stmt.setString(6, String.join(",", plan.getRecommendations()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        plan.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public CustomizedPlan getPlanById(int id) throws SQLException {
        String sql = "SELECT * FROM CustomizedPlan WHERE id = ?";
        CustomizedPlan plan = null;

        // Variables para almacenar datos fuera del ResultSet
        int planId = 0;
        String name = null;
        String description = null;
        String race = null;
        int behavior = 0;
        List<String> recommendations = new ArrayList<>();
        int petId = 0;

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 1. Extraer todos los datos del ResultSet
                    planId = rs.getInt("id");
                    name = rs.getString("name");
                    description = rs.getString("description");
                    race = rs.getString("race");
                    behavior = rs.getInt("behavior");
                    String recommendationsStr = rs.getString("recommendations");
                    petId = rs.getInt("pet_id");

                    // 2. Convertir recomendaciones a List<String>
                    recommendations = (recommendationsStr != null && !recommendationsStr.isEmpty())
                            ? Arrays.asList(recommendationsStr.split(","))
                            : new ArrayList<>();
                }
            } // El ResultSet se cierra aquí automáticamente
        }

        // 3. Si se encontró el plan, obtener el Pet y construirlo
        if (planId != 0) {
            Pet pet = petDAO.buscarPorId(petId); // Ahora petId es accesible
            plan = new CustomizedPlan(planId, name, description, race, behavior, recommendations, pet);
        }

        return plan;
    }

    public List<CustomizedPlan> getAllCustomizedPlans() throws SQLException {
        List<CustomizedPlan> plans = new ArrayList<>();
        String sql = "SELECT cp.*, p.name as pet_name FROM CustomizedPlan cp LEFT JOIN Pet p ON cp.pet_id = p.id";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int petId = rs.getInt("pet_id");
                Pet pet = null;
                if (petId > 0) {
                    pet = new Pet(petId);
                    pet.setName(rs.getString("pet_name"));
                }

                String recommendationsStr = rs.getString("recommendations");
                List<String> recommendations = (recommendationsStr != null && !recommendationsStr.isEmpty())
                        ? Arrays.asList(recommendationsStr.split(","))
                        : new ArrayList<>();

                plans.add(new CustomizedPlan(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("race"),
                        rs.getInt("behavior"),
                        recommendations,
                        pet
                ));
            }
        }
        return plans;
    }
}
