package View;

import Model.Medicine;
import Model.Supplier;
import java.util.Date;
import java.util.Scanner;

public class MedicineView {
    private Scanner scanner;

    public MedicineView() {
        scanner = new Scanner(System.in);
    }

    public Supplier getSupplierDetails() {
        System.out.println("\n--- REGISTRO DE PROVEEDOR ---");
        System.out.print("Nombre del proveedor: ");
        String name = scanner.nextLine();
        
        System.out.print("Dirección: ");
        String address = scanner.nextLine();
        
        System.out.print("Teléfono: ");
        String cellphone = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        return new Supplier(name, address, cellphone, email);
    }

    public Medicine getMedicineDetails(int supplierId) {
        System.out.println("\n--- REGISTRO DE MEDICAMENTO ---");
        System.out.print("Nombre del medicamento: ");
        String name = scanner.nextLine();
        
        System.out.print("Tipo (antibiótico, antiinflamatorio, etc.): ");
        String type = scanner.nextLine();
        
        System.out.print("Fabricante: ");
        String manufacturer = scanner.nextLine();
        
        int stock = 0;
        while (true) {
            System.out.print("Cantidad en stock: ");
            try {
                stock = Integer.parseInt(scanner.nextLine());
                if (stock >= 0) break;
                System.out.println("Error: La cantidad no puede ser negativa.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }
        
        Date expirationDate = null;
        while (expirationDate == null) {
            System.out.print("Fecha de vencimiento (YYYY-MM-DD): ");
            try {
                String dateStr = scanner.nextLine();
                expirationDate = java.sql.Date.valueOf(dateStr);
                
                // Validar que no sea una fecha pasada
                if (expirationDate.before(new Date())) {
                    System.out.println("Error: La fecha de vencimiento no puede ser anterior a hoy.");
                    expirationDate = null;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Formato de fecha inválido. Use YYYY-MM-DD");
            }
        }
        
        return new Medicine(name, type, manufacturer, stock, expirationDate, supplierId);
    }

    public void displaySuccessMessage(String message) {
        System.out.println("\nÉXITO: " + message);
    }

    public void displayErrorMessage(String message) {
        System.out.println("\nERROR: " + message);
    }

    public int getMedicineId() {
        System.out.print("\nIngrese ID del medicamento: ");
        return Integer.parseInt(scanner.nextLine());
    }
}