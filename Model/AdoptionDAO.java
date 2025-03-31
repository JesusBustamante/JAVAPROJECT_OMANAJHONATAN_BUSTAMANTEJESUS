package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdoptionDAO {
    
    public boolean registerAdoption(int adopterId, int petId) {
        String sql = "INSERT INTO Adoption (id, petAdopted_id, date) VALUES (?, ?, NOW())";
        String updatePet = "UPDATE PetAdoption SET adopted = true WHERE id = ?";

        try (Connection conn = BBDD_Connection.conectar();
             PreparedStatement stmtAdoption = conn.prepareStatement(sql);
             PreparedStatement stmtUpdatePet = conn.prepareStatement(updatePet)) {

            // Insertar la adopción
            stmtAdoption.setInt(1, adopterId);
            stmtAdoption.setInt(2, petId);
            int rowsInserted = stmtAdoption.executeUpdate();

            if (rowsInserted > 0) {
                // Actualizar estado de la mascota a adoptada
                stmtUpdatePet.setInt(1, petId);
                stmtUpdatePet.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
     public List<Adoption> obtenerAdopciones() {
        List<Adoption> adopciones = new ArrayList<>();
        String sql = "SELECT * FROM Adoption";

        try (Connection conn = BBDD_Connection.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Adoption adoption = new Adoption(
                        rs.getInt("id"),
                        rs.getDate("date"),
                        rs.getString("agreement"),
                        rs.getInt("petAdopted_id"),
                        rs.getInt("adopter_id")
                );
                adopciones.add(adoption);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al obtener las adopciones: " + e.getMessage());
        }
        return adopciones;
    }
}
