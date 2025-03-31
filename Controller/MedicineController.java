package Controller;

import Model.Medicine;
import Model.MedicineDAO;
import Model.Supplier;
import Model.SupplierDAO;
import View.MedicineView;
import java.sql.SQLException;
import java.util.Scanner;

public class MedicineController {
    private MedicineView view;
    private MedicineDAO medicineDAO;
    private SupplierDAO supplierDAO;
    private Scanner scanner;

    public MedicineController(MedicineView view) {
        this.view = view;
        this.scanner = new Scanner(System.in);
        try {
            this.medicineDAO = new MedicineDAO();
            this.supplierDAO = new SupplierDAO();
        } catch (SQLException e) {
            view.displayErrorMessage("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Nuevo método para mostrar el menú de medicamentos
    public void showMedicineMenu() {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n--- MENÚ DE MEDICAMENTOS ---");
            System.out.println("1. Registrar nuevo medicamento");
            System.out.println("2. Verificar stock y realizar pedido");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
                
                switch (option) {
                    case 1:
                        registerMedicine();
                        break;
                    case 2:
                        System.out.print("Ingrese ID del medicamento: ");
                        int medicineId = Integer.parseInt(scanner.nextLine());
                        checkStockAndOrder(medicineId);
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        view.displayErrorMessage("Opción no válida");
                }
            } catch (NumberFormatException e) {
                view.displayErrorMessage("Ingrese un número válido");
            }
        }
    }
    

    public void registerMedicine() {
    try {

        Supplier supplier = view.getSupplierDetails();
        int supplierId = supplierDAO.insertSupplier(supplier);
        supplier.setId(supplierId);
        
        Medicine medicine = view.getMedicineDetails(supplierId);
        boolean medicineSuccess = medicineDAO.insertMedicine(medicine);
        
        if (medicineSuccess) {
            view.displaySuccessMessage("Medicamento registrado exitosamente con ID de proveedor: " + supplierId);
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