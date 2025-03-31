package Model;

import java.sql.*;
import java.util.Date;

public class SupplyDAO {

    private Connection connection;

    public SupplyDAO() throws SQLException {
        this.connection = BBDD_Connection.conectar();
    }

    public boolean insertSupply(Supply supply) throws SQLException {
        String query = "INSERT INTO Supply (type, pack, stock, entryDate, expirationDate, supplier_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, supply.getType());
            stmt.setString(2, supply.getPack());
            stmt.setInt(3, supply.getStock());
            stmt.setDate(4, new java.sql.Date(supply.getEntryDate().getTime()));
            stmt.setDate(5, new java.sql.Date(supply.getExpirationDate().getTime()));
            stmt.setInt(6, supply.getSupplierId());
            return stmt.executeUpdate() > 0;
        } finally {
            BBDD_Connection.closeConnection(); 
        }
    }

    public boolean isNearExpiration(Date expirationDate) {
        Date today = new Date();
        long diffInMillies = expirationDate.getTime() - today.getTime();
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
        return diffInDays <= 30 && diffInDays >= 0;
    }
}
