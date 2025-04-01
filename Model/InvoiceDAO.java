/*package Model;

import java.sql.*;

public class InvoiceDAO {
    
    BBDD_Connection conexion = new BBDD_Connection();
    
    // Insertar usuario
    public void insert(Invoice invoice) {
        String sql = "INSERT INTO Invoice (date, subtotal, taxes, total, CUFE, QR, customer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(invoice.getDate()));
            stmt.setDouble(2, invoice.getSubtotal());
            stmt.setDouble(3, invoice.getTaxes());
            stmt.setDouble(4, invoice.getTotal());
            stmt.setString(5, invoice.getCUFE());
            stmt.setString(6, invoice.getQR());
            stmt.setInt(7, invoice.getOwner().getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BBDD_Connection.closeConnection(); 
        }
    }
}
*/