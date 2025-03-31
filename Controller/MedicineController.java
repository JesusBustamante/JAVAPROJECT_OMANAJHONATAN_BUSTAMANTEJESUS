package Controller;

import Model.Medicine;
import Model.MedicineDAO;
import Model.Supplier;
import Model.SupplierDAO;
import View.MedicineView;
import java.sql.SQLException;

public class MedicineController {
    private MedicineView view;
    private MedicineDAO medicineDAO;
    private SupplierDAO supplierDAO;

    public MedicineController(MedicineView view) {
        this.view = view;
        try {
            this.medicineDAO = new MedicineDAO();
            this.supplierDAO = new SupplierDAO();
        } catch (SQLException e) {
            view.displayErrorMessage("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public void registerMedicine() {
        try {
            // Primero registrar el proveedor si es necesario
            Supplier supplier = view.getSupplierDetails();
            int supplierId = supplierDAO.insertSupplier(supplier);
            supplier.setId(supplierId);
            
            // Luego registrar el medicamento
            Medicine medicine = view.getMedicineDetails(supplierId);
            boolean success = medicineDAO.insertMedicine(medicine);
            
            if (success) {
                view.displaySuccessMessage("Medicamento registrado exitosamente");
            } else {
                view.displayErrorMessage("Error al registrar el medicamento");
            }
        } catch (SQLException e) {
            view.displayErrorMessage("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            view.displayErrorMessage("Error: " + e.getMessage());
        }
    }

    public void checkStockAndOrder(int medicineId) {
        try {
            boolean ordered = supplierDAO.checkMedicineStockAndOrder(medicineId);
            if (ordered) {
                view.displaySuccessMessage("Pedido realizado automáticamente por bajo stock");
            }
        } catch (SQLException e) {
            view.displayErrorMessage("Error al verificar stock: " + e.getMessage());
        }
    }
}