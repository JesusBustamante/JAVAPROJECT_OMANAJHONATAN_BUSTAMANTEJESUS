package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    private Connection connection;
    private static final int MIN_MEDICINE_STOCK = 10;
    private static final int MIN_SUPPLY_STOCK = 5;

    public SupplierDAO() throws SQLException {
        this.connection = BBDD_Connection.conectar();
    }

    public int insertSupplier(Supplier supplier) throws SQLException {
    String query = "INSERT INTO Supplier (name, address, cellphone, email) VALUES (?, ?, ?, ?)";
    
    try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, supplier.getName());
        stmt.setString(2, supplier.getAddress());
        stmt.setString(3, supplier.getCellphone());
        stmt.setString(4, supplier.getEmail());
        
        int affectedRows = stmt.executeUpdate();
        
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    supplier.setId(id);
                    return id;
                }
            }
        }
        throw new SQLException("No se pudo insertar el proveedor, no se obtuvo ID");
    }
}

    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM Supplier";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
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
        String query = "SELECT * FROM Supplier WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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

    public void close() {
        BBDD_Connection.closeConnection();
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
