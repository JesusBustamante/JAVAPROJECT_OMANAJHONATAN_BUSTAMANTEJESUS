package View;

import Model.Supply;
import Model.Supplier;

import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class SupplyView {

    private final Scanner scanner = new Scanner(System.in);

    public Supply getSupplyDetails(Supplier supplier) {
        System.out.print("Ingrese el tipo de insumo: ");
        String type = scanner.nextLine();

        System.out.print("Ingrese el empaque del insumo: ");
        String pack = scanner.nextLine();

        System.out.print("Ingrese la fecha de entrada (YYYY-MM-DD): ");
        Date entryDate = java.sql.Date.valueOf(scanner.nextLine());

        System.out.print("Ingrese la fecha de vencimiento (YYYY-MM-DD): ");
        Date expirationDate = java.sql.Date.valueOf(scanner.nextLine());

        return new Supply(type, pack, entryDate, expirationDate, supplier);
    }

    public void displaySuppliesList(List<Supply> supplies) {
        if (supplies.isEmpty()) {
            System.out.println("No hay insumos registrados.");
        } else {
            System.out.println("\n--- LISTA DE INSUMOS ---");
            for (Supply supply : supplies) {
                System.out.printf("ID: %d, Tipo: %s, Empaque: %s, Vence: %s, Proveedor: %s%n",
                        supply.getId(), supply.getType(), supply.getPack(),
                        supply.getExpirationDate(), supply.getSupplier().getName());
            }
        }
    }

    public void displayExpiringSupplies(List<Supply> supplies) {
        if (supplies.isEmpty()) {
            System.out.println("No hay insumos próximos a vencer.");
        } else {
            System.out.println("\n--- ALERTA: INSUMOS POR VENCER ---");
            for (Supply supply : supplies) {
                System.out.printf("ID: %d, Tipo: %s, Vence en pocos días: %s, Proveedor: %s%n",
                        supply.getId(), supply.getType(), supply.getExpirationDate(), supply.getSupplier().getName());
            }
        }
    }

    public int getSupplierId() {
        System.out.print("Ingrese el ID del proveedor: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un número válido.");
            return -1;
        }
    }

    public void displayErrorMessage(String message) {
        System.out.println("Error: " + message);
    }
}
