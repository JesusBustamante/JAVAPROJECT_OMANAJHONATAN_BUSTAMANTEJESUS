package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplyDAO {
    private Connection conn;

    public SupplyDAO() throws SQLException {
        this.conn = BBDD_Connection.conectar();
    }

    public SupplyDAO(Connection externalConn) {
        this.conn = externalConn;
    }

    public boolean insertSupply(Supply supply) throws SQLException {
        String query = "INSERT INTO Supply (type, pack, entryDate, expirationDate, supplier_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, supply.getType());
            stmt.setString(2, supply.getPack());
            stmt.setDate(3, new java.sql.Date(supply.getEntryDate().getTime()));
            stmt.setDate(4, new java.sql.Date(supply.getExpirationDate().getTime()));
            stmt.setInt(5, supply.getSupplier().getId()); 

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        supply.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public List<Supply> getAllSupplies(SupplierDAO supplierDAO) throws SQLException {
        List<Supply> supplies = new ArrayList<>();
        String query = "SELECT * FROM Supply";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                supplies.add(mapResultSetToSupply(rs, supplierDAO));
            }
        }
        return supplies;
    }

    public List<Supply> getExpiringSupplies(SupplierDAO supplierDAO) throws SQLException {
        List<Supply> expiringSupplies = new ArrayList<>();
        String query = "SELECT * FROM Supply WHERE expirationDate <= DATE_ADD(CURDATE(), INTERVAL 7 DAY)";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                expiringSupplies.add(mapResultSetToSupply(rs, supplierDAO));
            }
        }
        return expiringSupplies;
    }

    private Supply mapResultSetToSupply(ResultSet rs, SupplierDAO supplierDAO) throws SQLException {
        int supplierId = rs.getInt("supplier_id");
        Supplier supplier = supplierDAO.getSupplierById(supplierId); // Obtiene el objeto Supplier

        return new Supply(
                rs.getString("type"),
                rs.getString("pack"),
                rs.getDate("entryDate"),
                rs.getDate("expirationDate"),
                supplier
        );
    }
}
