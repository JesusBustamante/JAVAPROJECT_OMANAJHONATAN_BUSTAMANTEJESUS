package View;

import Model.Supplier;
import java.util.List;
import java.util.Scanner;

public class SupplierView {
    private Scanner scanner;

    public SupplierView() {
        this.scanner = new Scanner(System.in);
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

    public void displaySupplierDetails(Supplier supplier) {
        System.out.println("\n--- DETALLES DEL PROVEEDOR ---");
        System.out.println("ID: " + supplier.getId());
        System.out.println("Nombre: " + supplier.getName());
        System.out.println("Dirección: " + supplier.getAddress());
        System.out.println("Teléfono: " + supplier.getCellphone());
        System.out.println("Email: " + supplier.getEmail());
    }

    public void displaySuppliersList(List<Supplier> suppliers) {
        System.out.println("\n--- LISTA DE PROVEEDORES ---");
        if (suppliers.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
        } else {
            for (Supplier supplier : suppliers) {
                System.out.println("ID: " + supplier.getId() + " - Nombre: " + supplier.getName());
            }
        }
    }

    public int getSupplierId() {
        System.out.print("\nIngrese ID del proveedor: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void displaySuccessMessage(String message) {
        System.out.println("\nÉXITO: " + message);
    }

    public void displayErrorMessage(String message) {
        System.out.println("\nERROR: " + message);
    }
}
