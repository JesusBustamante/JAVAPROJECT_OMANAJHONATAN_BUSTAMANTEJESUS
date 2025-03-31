package Controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class MainController {

    private PetAdoptionController petController;
    private MedicineController medController;
    private SupplyController supplyController;
    private SupplierController supplierController;
    private Scanner scanner;

    public MainController() throws SQLException {
        this.petController = new PetAdoptionController();
        this.medController = new MedicineController();
        this.supplyController = new SupplyController();
        this.supplierController = new SupplierController();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("\n=== SISTEMA DE GESTIÓN VETERINARIA ===");

        // 1. Registrar mascota y adoptante
        System.out.println("\n--- REGISTRO DE MASCOTA Y ADOPTANTE ---");
        registerPetAndAdopter();

        // 2. Registrar medicamento
        System.out.println("\n--- REGISTRO DE MEDICAMENTO ---");
        registerMedicine();

        // 3. Registrar insumo
        System.out.println("\n--- REGISTRO DE INSUMO ---");
        registerSupply();

        // 4. Verificar stock
        System.out.println("\n--- VERIFICACIÓN DE STOCK ---");
        checkStock();

        scanner.close();
    }

    private String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido");
            }
        }
    }

    private Date readDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " (YYYY-MM-DD): ");
                return java.sql.Date.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Formato inválido. Use YYYY-MM-DD");
            }
        }
    }

    private void registerPetAndAdopter() {
        // Datos mascota
        String petName = readString("Nombre de la mascota");
        String species = readString("Especie (dog/cat/etc)");
        String race = readString("Raza");
        int age = readInt("Edad");
        String gender = readString("Género (MALE/FEMALE)");
        String description = readString("Descripción");
        String photo = readString("Foto (nombre archivo)");
        String healthStatus = readString("Estado de salud");

        // Datos adoptante
        String personalId = readString("ID del adoptante");
        String fullName = readString("Nombre completo");
        String address = readString("Dirección");
        String cellphone = readString("Teléfono");
        String email = readString("Email");
        String reference = readString("Referencia personal");

        /*petController.registerPetAndAdopter(
                petName, species, race, age, null, gender,
                description, photo, healthStatus,
                personalId, fullName, address, cellphone,
                email, reference
        );*/
    }

    private void registerMedicine() {
        String name = readString("Nombre del medicamento");
        String type = readString("Tipo");
        String manufacturer = readString("Fabricante");
        int stock = readInt("Stock disponible");
        Date expirationDate = readDate("Fecha de expiración");

        String supplierName = readString("Nombre del proveedor");
        String supplierAddress = readString("Dirección del proveedor");
        String supplierCellphone = readString("Teléfono del proveedor");
        String supplierEmail = readString("Email del proveedor");

        medController.registerMedicine(
                name, type, manufacturer, stock, expirationDate,
                supplierName, supplierAddress, supplierCellphone, supplierEmail
        );
    }

    private void registerSupply() {
        String type = readString("Tipo de insumo");
        String pack = readString("Paquete/Unidad");
        int stock = readInt("Stock disponible");
        Date entryDate = readDate("Fecha de ingreso");
        Date expirationDate = readDate("Fecha de expiración");
        int supplierId = readInt("ID del proveedor");

        supplyController.registerSupply(
                type, pack, stock, entryDate, expirationDate, supplierId
        );
    }

    private void checkStock() {
        int medicineId = readInt("ID del medicamento a verificar");
        int supplyId = readInt("ID del insumo a verificar");

        supplierController.checkStockAndOrder(medicineId, supplyId);
    }
}
