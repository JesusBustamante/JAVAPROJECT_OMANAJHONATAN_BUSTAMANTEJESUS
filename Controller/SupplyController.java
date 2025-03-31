package Controller;

import Model.Supply;
import Model.SupplyDAO;
import View.ConsoleView;
import java.sql.SQLException;
import java.util.Date;

public class SupplyController {
    private SupplyDAO dao;
    private ConsoleView view;
    
    public SupplyController() throws SQLException {
        this.dao = new SupplyDAO();
        this.view = new ConsoleView();
    }
    
    public void registerSupply(String type, String pack, int stock, Date entryDate, 
                              Date expirationDate, int supplierId) {
        try {
            if (expirationDate.before(new Date())) {
                throw new IllegalArgumentException("Expiration date cannot be in the past");
            }
            if (stock < 0) {
                throw new IllegalArgumentException("Stock cannot be negative");
            }
            
            Supply supply = new Supply(type, pack, stock, entryDate, expirationDate, supplierId);
            if (!dao.insertSupply(supply)) {
                throw new SQLException("Failed to register supply");
            }
            view.showSupplySuccess(supply);
        } catch (SQLException e) {
            view.showError(e.getMessage());
        } catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString(); 
    }
    
    

    
}
    

