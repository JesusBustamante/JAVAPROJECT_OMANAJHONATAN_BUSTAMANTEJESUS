package Controller;

import Model.BBDD_Connection;
import Model.Medicine;
import Model.MedicineDAO;
import Model.SupplierDAO;
import View.MedicineView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MedicineController {
    private final MedicineView view;
    private MedicineDAO medicineDAO;
    private SupplierDAO supplierDAO;
    private final Scanner scanner;

    // Constructor con DAOs inyectados (para pruebas o cuando ya hay conexión abierta)
    public MedicineController(MedicineView view, MedicineDAO medicineDAO, SupplierDAO supplierDAO) {
        this.view = view;
        this.medicineDAO = medicineDAO;
        this.supplierDAO = supplierDAO;
        this.scanner = new Scanner(System.in);
    }

    // Constructor principal con manejo seguro de la conexión
    public MedicineController(MedicineView view) {
        this.view = view;
        this.scanner = new Scanner(System.in);

        try {
            Connection conn = BBDD_Connection.conectar();
            this.medicineDAO = new MedicineDAO(conn);
            this.supplierDAO = new SupplierDAO(conn);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo establecer la conexión con la base de datos", e);
        }
    }

    // Constructor por defecto
    public MedicineController() {
        this(new MedicineView());
    }

    // Menú de opciones
    public void showMedicineMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- MENÚ DE MEDICAMENTOS ---");
            System.out.println("1. Registrar nuevo medicamento");
            System.out.println("2. Verificar stock");
            System.out.println("3. Listar todos los medicamentos");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                int option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        registerMedicine();
                        break;
                    case 2:
                        checkStock();
                        break;
                    case 3:
                        listAllMedicines();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        view.displayErrorMessage("Opción no válida");
                }
            } catch (NumberFormatException e) {
                view.displayErrorMessage("Ingrese un número válido");
            } catch (SQLException e) {
                view.displayErrorMessage("Error de base de datos: " + e.getMessage());
            }
        }
    }

    // Método para registrar un nuevo medicamento
    private void registerMedicine() throws SQLException {
        try {
            Medicine medicine = view.getMedicineDetails();

            if (supplierDAO.getSupplierById(medicine.getSupplierId()) == null) {
                view.displayErrorMessage("Error: El proveedor con ID " + medicine.getSupplierId() + " no existe.");
                return;
            }

            if (medicineDAO.insertMedicine(medicine)) {
                view.displaySuccessMessage("Medicamento registrado exitosamente");
            } else {
                view.displayErrorMessage("Error al registrar el medicamento");
            }
        } catch (Exception e) {
            view.displayErrorMessage("Error: " + e.getMessage());
            throw e;
        }
    }

    // Método para verificar stock
    private void checkStock() throws SQLException {
        int medicineId = view.getMedicineId();
        Medicine medicine = medicineDAO.getMedicineById(medicineId);

        if (medicine == null) {
            view.displayErrorMessage("Medicamento no encontrado");
            return;
        }

        if (medicine.isLowStock()) {
            view.displayErrorMessage("Stock bajo: " + medicine.getStock() + " unidades");
        } else {
            view.displaySuccessMessage("Stock suficiente: " + medicine.getStock() + " unidades");
        }

        if (medicine.isExpired()) {
            view.displayErrorMessage("ATENCIÓN: Este medicamento está vencido");
        }
    }

    // Método para listar todos los medicamentos
    private void listAllMedicines() throws SQLException {
        var medicines = medicineDAO.getAllMedicines();
        if (medicines.isEmpty()) {
            view.displayErrorMessage("No hay medicamentos registrados");
            return;
        }

        System.out.println("\n--- LISTA DE MEDICAMENTOS ---");
        for (Medicine m : medicines) {
            System.out.printf("ID: %d, Nombre: %s, Tipo: %s, Stock: %d, Vencimiento: %s%n",
                    m.getId(), m.getName(), m.getType(), m.getStock(), m.getExpirationDate());
            if (m.isLowStock()) {
                System.out.println("  [STOCK BAJO]");
            }
            if (m.isExpired()) {
                System.out.println("  [VENCIDO]");
            }
        }
    }
}
