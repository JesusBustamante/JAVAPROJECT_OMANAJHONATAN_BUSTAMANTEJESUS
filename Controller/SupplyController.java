package Controller;

import Model.Supply;
import Model.SupplyDAO;
import Model.Supplier;
import Model.SupplierDAO;
import View.SupplyView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SupplyController {

    private final SupplyView view;
    private final SupplyDAO supplyDAO;
    private final SupplierDAO supplierDAO;
    private final Scanner scanner = new Scanner(System.in);

    public SupplyController() throws SQLException {
        this.view = new SupplyView();
        this.supplyDAO = new SupplyDAO();
        this.supplierDAO = new SupplierDAO();
    }

    public void showSupplyMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- MENÚ DE INSUMOS ---");
            System.out.println("1. Registrar nuevo insumo");
            System.out.println("2. Listar todos los insumos");
            System.out.println("3. Mostrar alertas de insumos por vencer");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                int option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        registerSupply();
                        break;
                    case 2:
                        listAllSupplies();
                        break;
                    case 3:
                        showExpiringSupplies();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido");
            } catch (SQLException e) {
                System.out.println("Error de base de datos: " + e.getMessage());
            }
        }
    }

    private void registerSupply() throws SQLException {
        int supplierId = view.getSupplierId();
        Supplier supplier = supplierDAO.getSupplierById(supplierId);
        Supply supply = view.getSupplyDetails(supplier);

        if (supplierId <= 0) {
            view.displayErrorMessage("ID de proveedor no válido.");
            return;
        }

    }

    private void listAllSupplies() throws SQLException {
        List<Supply> supplies = supplyDAO.getAllSupplies(supplierDAO);
        view.displaySuppliesList(supplies);
    }

    private void showExpiringSupplies() throws SQLException {
        List<Supply> expiringSupplies = supplyDAO.getExpiringSupplies(supplierDAO);
        view.displayExpiringSupplies(expiringSupplies);
    }

}
