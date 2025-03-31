package Model;

import java.sql.*;

public class SupplierDAO {

    private Connection connection;
    private static final int MIN_MEDICINE_STOCK = 10;
    private static final int MIN_SUPPLY_STOCK = 5;

    public SupplierDAO() throws SQLException {
        this.connection = BBDD_Connection.conectar();
    }

    public boolean checkMedicineStockAndOrder(int medicineId) throws SQLException {
        try {
            String query = "SELECT stock, supplier_id FROM Medicine WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, medicineId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                int supplierId = rs.getInt("supplier_id");
                if (stock < MIN_MEDICINE_STOCK) {
                    int quantity = MIN_MEDICINE_STOCK * 2 - stock;
                    return generatePurchaseOrder(supplierId, "Medicine", medicineId, quantity);
                }
                return false;
            }
            throw new SQLException("Medicine not found");
        } finally {
            BBDD_Connection.closeConnection();
        }
    }

    public boolean checkSupplyStockAndOrder(int supplyId) throws SQLException {
        try {
            String query = "SELECT stock, supplier_id FROM Supply WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, supplyId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                int supplierId = rs.getInt("supplier_id");
                if (stock < MIN_SUPPLY_STOCK) {
                    int quantity = MIN_SUPPLY_STOCK * 2 - stock;
                    return generatePurchaseOrder(supplierId, "Supply", supplyId, quantity);
                }
                return false;
            }
            throw new SQLException("Supply not found");
        } finally {
            BBDD_Connection.closeConnection();
        }
    }

    private boolean generatePurchaseOrder(int supplierId, String itemType, int itemId, int quantity)
            throws SQLException {
        String query = "INSERT INTO PurchaseOrder (supplier_id, item_type, item_id, quantity, order_date) "
                + "VALUES (?, ?, ?, ?, CURDATE())";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, supplierId);
            stmt.setString(2, itemType);
            stmt.setInt(3, itemId);
            stmt.setInt(4, quantity);
            return stmt.executeUpdate() > 0;
        }
        
    }
}
