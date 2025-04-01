package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
    
    private Connection conn;

    public SupplierDAO() throws SQLException {
        this.conn = BBDD_Connection.conectar();
    }

    public SupplierDAO(Connection externalConn) {
        this.conn = externalConn;
    }

    public int insertSupplier(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO Supplier (name, address, cellphone, email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getAddress());
            stmt.setString(3, supplier.getCellphone());
            stmt.setString(4, supplier.getEmail());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Supplier supplier = new Supplier(
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("cellphone"),
                    rs.getString("email")
                );
                supplier.setId(rs.getInt("id"));
                suppliers.add(supplier);
            }
        }
        return suppliers;
    }

    public Supplier getSupplierById(int id) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Supplier supplier = new Supplier(
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("cellphone"),
                        rs.getString("email")
                    );
                    supplier.setId(rs.getInt("id"));
                    return supplier;
                }
            }
        }
        return null;
    }
}
