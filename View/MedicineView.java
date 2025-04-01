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

    public Medicine getMedicineDetails() { 
        System.out.println("\n--- REGISTRO DE MEDICAMENTO ---");
        System.out.print("Nombre del medicamento: ");
        String name = scanner.nextLine();

        System.out.print("Tipo (antibiótico, antiinflamatorio, etc.): ");
        String type = scanner.nextLine();

        System.out.print("Fabricante: ");
        String manufacturer = scanner.nextLine();

        int stock = getPositiveInteger("Cantidad en stock: ");
        Date expirationDate = getValidFutureDate("Fecha de vencimiento (YYYY-MM-DD): ");

        int supplierId = getPositiveInteger("ID del proveedor existente: ");

        return new Medicine(name, type, manufacturer, stock, expirationDate, supplierId);
    }

    private int getPositiveInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= 0) {
                    return value;
                }
                System.out.println("Error: El valor no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }
    }

    private Date getValidFutureDate(String prompt) {
        Date date = null;
        while (date == null) {
            System.out.print(prompt);
            try {
                String dateStr = scanner.nextLine();
                date = java.sql.Date.valueOf(dateStr);

                if (date.before(new Date())) {
                    System.out.println("Error: La fecha no puede ser anterior a hoy.");
                    date = null;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Formato inválido. Use YYYY-MM-DD");
            }
        }
        return date;
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
