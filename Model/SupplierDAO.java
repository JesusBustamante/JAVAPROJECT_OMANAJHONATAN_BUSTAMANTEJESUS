package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public int insertSupplier(Supplier supplier) {
        String sql = "INSERT INTO Supplier (name, address, cellphone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = BBDD_Connection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }
        return -1;
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";
        
        try (Connection conn = BBDD_Connection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }
        return suppliers;
    }

    public Supplier getSupplierById(int id) {
        String sql = "SELECT * FROM Supplier WHERE id = ?";
        
        try (Connection conn = BBDD_Connection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection();
        }
        return null;
    }
}
