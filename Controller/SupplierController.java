package Controller;

import Model.Supplier;
import Model.SupplierDAO;
import View.SupplierView;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SupplierController {

    private final SupplierView view;
    private final SupplierDAO supplierDAO;
    private final Scanner scanner = new Scanner(System.in);

    public SupplierController() {
        this.view = new SupplierView();
        SupplierDAO tempDAO;
        try {
            tempDAO = new SupplierDAO();
        } catch (SQLException e) {
            view.displayErrorMessage("Error al conectar con la base de datos: " + e.getMessage());
            tempDAO = null;
        }
        this.supplierDAO = tempDAO;
    }

    public void showSupplierMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- MENÚ DE PROVEEDORES ---");
            System.out.println("1. Registrar nuevo proveedor");
            System.out.println("2. Listar todos los proveedores");
            System.out.println("3. Buscar proveedor por ID");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        registerSupplier();
                        break;
                    case 2:
                        getAllSuppliers();
                        break;
                    case 3:
                        getSupplierById();
                        break;
                    case 4:
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

    private void registerSupplier() {
        Supplier supplier = view.getSupplierDetails();
        
        try {
            int supplierId = supplierDAO.insertSupplier(supplier);
            if (supplierId > 0) {
                view.displaySuccessMessage("Proveedor registrado exitosamente con ID: " + supplierId);
            } else {
                view.displayErrorMessage("Error al registrar el proveedor");
            }
        } catch (SQLException e) {
            view.displayErrorMessage("Error en la base de datos: " + e.getMessage());
        }
    }

    private void getAllSuppliers() {
        try {
            List<Supplier> suppliers = supplierDAO.getAllSuppliers();
            if (suppliers.isEmpty()) {
                view.displayErrorMessage("No hay proveedores registrados.");
            } else {
                view.displaySuppliersList(suppliers);
            }
        } catch (SQLException e) {
            view.displayErrorMessage("Error en la base de datos: " + e.getMessage());
        }
    }

    private void getSupplierById() {
        int id = view.getSupplierId();
        try {
            Supplier supplier = supplierDAO.getSupplierById(id);
            if (supplier != null) {
                view.displaySupplierDetails(supplier);
            } else {
                view.displayErrorMessage("Proveedor no encontrado.");
            }
        } catch (SQLException e) {
            view.displayErrorMessage("Error en la base de datos: " + e.getMessage());
        }
    }

    public Supplier getSupplier(int supplierId) {
        try {
            return supplierDAO.getSupplierById(supplierId);
        } catch (SQLException e) {
            view.displayErrorMessage("Error en la base de datos: " + e.getMessage());
            return null;
        }
    }
}
