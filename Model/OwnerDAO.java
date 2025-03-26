package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerDAO {

    BBDD_Connection conexion = new BBDD_Connection();

    // Insertar usuario
    public int insertar(Owner owner) {
        String sql = "INSERT INTO Owner (personalId, nit, fullName, email, cellphone, address, emergencyContact) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, owner.getPersonal_id());
            stmt.setString(2, owner.getNIT());
            stmt.setString(3, owner.getFull_name());
            stmt.setString(4, owner.getEmail());
            stmt.setString(5, owner.getCellphone());
            stmt.setString(6, owner.getAddress());
            stmt.setString(7, owner.getEmergency_contact());
            stmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    owner.setId(generatedId); // Asignamos el ID al objeto Owner
                    return generatedId; // Devolvemos el ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 si hubo un error

    }

    public Owner buscarPorPersonalId(int personalId) {
        String sql = "SELECT * FROM Owner WHERE personalId = ?";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, personalId);
            try (ResultSet rs = stmt.executeQuery()) {
                
                
                if (rs.next()) {
                    return new Owner(
                        rs.getInt("id"),
                        rs.getInt("personalId"),
                        rs.getString("nit"),
                        rs.getString("fullName"),
                        rs.getString("address"),
                        rs.getString("cellphone"),
                        rs.getString("email"),
                        rs.getString("emergencyContact")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Owner> obtenerTodos() {
        List<Owner> lista = new ArrayList<>();
        String sql = "SELECT o.id AS owner_id, o.personalId, o.nit, o.fullName, o.address, o.cellphone, o.email, o.emergencyContact, "
                + "p.id AS pet_id, p.name, p.species, p.race, p.age, p.birthDate, p.gender, p.microchip, p.tattoo, p.photo "
                + "FROM Owner o "
                + "LEFT JOIN Pet p ON o.id = p.owner_id"; // Usar LEFT JOIN para incluir dueños sin mascotas

        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            Owner currentOwner = null;
            while (rs.next()) {
                int ownerId = rs.getInt("owner_id");

                // Si el dueño actual no coincide, crear un nuevo dueño
                if (currentOwner == null || currentOwner.getId() != ownerId) {
                    currentOwner = new Owner(
                            ownerId,
                            rs.getInt("personalId"),
                            rs.getString("nit"),
                            rs.getString("fullName"),
                            rs.getString("address"),
                            rs.getString("cellphone"),
                            rs.getString("email"),
                            rs.getString("emergencyContact")
                    );
                    lista.add(currentOwner);
                }

                // Si hay una mascota asociada, agregarla al dueño
                int petId = rs.getInt("pet_id");
                if (currentOwner != null && petId > 0) { // Verificar si hay una mascota
                    Pet pet = new Pet(
                        petId,
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getString("race"),
                        rs.getInt("age"),
                        rs.getDate("birthDate").toLocalDate(),
                        Gender.valueOf(rs.getString("gender")), // Usar el enum Gender
                        rs.getString("microchip"),
                        rs.getBoolean("tattoo"),
                        rs.getString("photo"),
                        currentOwner
                    );
                    currentOwner.getPets().add(pet); // Agregar la mascota al dueño
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
