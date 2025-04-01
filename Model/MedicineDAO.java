package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {

    private Connection conn;
    private boolean isExternalConnection;

    // Constructor para conexión interna (crea su propia conexión)
    public MedicineDAO() throws SQLException {
        this.conn = BBDD_Connection.conectar();
        this.isExternalConnection = false;
    }

    // Constructor para conexión externa (para compartir conexión entre DAOs)
    public MedicineDAO(Connection externalConn) {
        this.conn = externalConn;
        this.isExternalConnection = true;
    }

    public boolean insertMedicine(Medicine medicine) throws SQLException {
        String query = "INSERT INTO Medicine (name, type, manufacturer, stock, expirationDate, supplier_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, medicine.getName());
            stmt.setString(2, medicine.getType());
            stmt.setString(3, medicine.getManufacturer());
            stmt.setInt(4, medicine.getStock());
            stmt.setDate(5, new java.sql.Date(medicine.getExpirationDate().getTime()));
            stmt.setInt(6, medicine.getSupplierId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        medicine.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    public List<Medicine> getAllMedicines() throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        String query = "SELECT * FROM Medicine";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                medicines.add(mapResultSetToMedicine(rs));
            }
        }
        return medicines;
    }

    public Medicine getMedicineById(int id) throws SQLException {
        String query = "SELECT * FROM Medicine WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedicine(rs);
                }
            }
        }
        return null;
    }

    public boolean updateStock(int medicineId, int quantity) throws SQLException {
        String query = "UPDATE Medicine SET stock = stock + ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, medicineId);
            return stmt.executeUpdate() > 0;
        }
    }

    public void rollback() {
        try {
            if (conn != null && !conn.getAutoCommit()) {
                conn.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Error durante rollback: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autoCommit: " + e.getMessage());
            }
        }
    }

    public void beginTransaction() throws SQLException {
        conn.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        conn.commit();
        conn.setAutoCommit(true);
    }

    private Medicine mapResultSetToMedicine(ResultSet rs) throws SQLException {
        return new Medicine(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("manufacturer"),
                rs.getInt("stock"),
                rs.getDate("expirationDate"),
                rs.getInt("supplier_id")
        );
    }

}
