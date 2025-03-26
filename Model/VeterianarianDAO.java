package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterianarianDAO {

    private BBDD_Connection conexion = new BBDD_Connection();

    // Insertar veterinario
    public void insertar(Veterinarian veterinarian) {
        String sql = "INSERT INTO Veterinarian (name, specialization) VALUES (?, ?)";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, veterinarian.getName());
            stmt.setString(2, veterinarian.getSpecialization());
            stmt.executeUpdate();

            // Obtener ID generado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    veterinarian.setId(rs.getInt(1));
                }
            }

            System.out.println("✅ Veterinario " + veterinarian.getName() + " registrado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }
    }

    // Obtener todos los veterinarios
    public List<Veterinarian> obtenerTodos() {
        List<Veterinarian> lista = new ArrayList<>();
        String sql = "SELECT * FROM Veterinarian ORDER BY id ASC";

        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Veterinarian(rs.getInt("id"), rs.getString("name"), rs.getString("specialization")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection(); // 🔹 Cerrar conexión después de la operación
        }
        return lista;
    }
}
