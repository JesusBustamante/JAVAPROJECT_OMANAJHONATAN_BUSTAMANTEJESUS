package Model;

import java.sql.*;

public class AdopterDAO {

    public boolean addAdopter(Adopter adopter) {
        String sql = "INSERT INTO Adopter (personalId, fullName, address, cellphone, email, personalReference) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = BBDD_Connection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adopter.getPersonalId());
            stmt.setString(2, adopter.getFullName());
            stmt.setString(3, adopter.getAddress());
            stmt.setString(4, adopter.getCellphone());
            stmt.setString(5, adopter.getEmail());
            stmt.setString(6, adopter.getReference());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Adopter getAdopterById(int adopterId) {
        String sql = "SELECT * FROM Adopter WHERE id = ?";
        try (Connection conn = BBDD_Connection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, adopterId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Adopter(
                    rs.getInt("id"),
                    rs.getString("personalId"),
                    rs.getString("fullName"),
                    rs.getString("address"),
                    rs.getString("cellphone"),
                    rs.getString("email"),
                    rs.getString("personalReference")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
