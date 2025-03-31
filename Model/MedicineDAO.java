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

    public MedicineDAO() throws SQLException {
        this.conn = BBDD_Connection.conectar();
    }

    public void beginTransaction() throws SQLException {
        conn.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        conn.commit();
        conn.setAutoCommit(true);
    }

    public void rollback() {
        try {
            if (conn != null) {
                conn.rollback();
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Error during rollback: " + e.getMessage());
        } finally {
            BBDD_Connection.closeConnection();
        }
    }

    public int insertSupplier(Supplier supplier) throws SQLException {
        try {
            String query = "INSERT INTO Supplier (name, address, cellphone, email) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getAddress());
            stmt.setString(3, supplier.getCellphone());
            stmt.setString(4, supplier.getEmail());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                supplier.setId(rs.getInt(1));
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            rollback();
            throw e;
        } finally {
            BBDD_Connection.closeConnection();
        }
    }

    public boolean insertMedicine(Medicine medicine) throws SQLException {
        try {
            String query = "INSERT INTO Medicine (name, type, manufacturer, stock, expirationDate, supplier_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, medicine.getName());
            stmt.setString(2, medicine.getType());
            stmt.setString(3, medicine.getManufacturer());
            stmt.setInt(4, medicine.getStock());
            stmt.setDate(5, new java.sql.Date(medicine.getExpirationDate().getTime()));
            stmt.setInt(6, medicine.getSupplierId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            rollback();
            throw e;
        } finally {
            BBDD_Connection.closeConnection();
        }
    }

    public List<Medicine> getAllMedicines() throws SQLException {
        List<Medicine> medicines = new ArrayList<>();
        String query = "SELECT * FROM Medicine";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Medicine medicine = new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("manufacturer"),
                        rs.getInt("stock"),
                        rs.getDate("expirationDate"),
                        rs.getInt("supplier_id")
                );
                medicines.add(medicine);
            }
        }
        return medicines;
    }

    public Medicine getMedicineById(int id) throws SQLException {
        String query = "SELECT * FROM Medicine WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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

    public void close() {
        BBDD_Connection.closeConnection();
    }
}
