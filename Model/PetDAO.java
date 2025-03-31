package Model;

import java.sql.*;

public class PetDAO {

    BBDD_Connection conexion = new BBDD_Connection();

    // Insertar usuario
    public void insertar(Pet pet) {
        String sql = "INSERT INTO Pet (name, species, race, age, birthDate, gender, microchip, tattoo, photo, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pet.getName());
            stmt.setString(2, pet.getSpecies());
            stmt.setString(3, pet.getRace());
            stmt.setInt(4, pet.getAge());
            stmt.setDate(5, java.sql.Date.valueOf(pet.getBirth_date()));
            stmt.setString(6, pet.getGender().name());
            stmt.setString(7, pet.getMicrochip());
            stmt.setBoolean(8, pet.getTattoo());
            stmt.setString(9, pet.getPhoto_url());
            stmt.setInt(10, pet.getOwner().getId());
            stmt.executeUpdate();

            

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection(); // 🔹 Cerrar conexión después de la operación
        }
    }

    public Pet buscarPorId(int id) {
        String sql = "SELECT p.*, o.id AS owner_id, o.fullName, o.personalId, o.nit, o.address, o.cellphone, o.email, o.emergencyContact "
                + "FROM Pet p "
                + "JOIN Owner o ON p.owner_id = o.id "
                + "WHERE p.id = ?";

        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear el objeto Owner
                    Owner owner = new Owner(
                            rs.getInt("owner_id"),
                            rs.getInt("personalId"),
                            rs.getString("nit"),
                            rs.getString("fullName"),
                            rs.getString("address"),
                            rs.getString("cellphone"),
                            rs.getString("email"),
                            rs.getString("emergencyContact")
                    );

                    // Crear y retornar la mascota con su dueño asignado
                    return new Pet(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("species"),
                            rs.getString("race"),
                            rs.getInt("age"),
                            rs.getDate("birthDate").toLocalDate(),
                            Gender.valueOf(rs.getString("gender")),
                            rs.getString("microchip"),
                            rs.getBoolean("tattoo"),
                            rs.getString("photo"),
                            owner // Ahora la mascota incluye su dueño
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }
        return null;
    }
}
