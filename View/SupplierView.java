package View;

import Model.Supplier;
import java.util.List;

public class SupplierView {
    public Supplier getSupplierDetails() {
        System.out.println("\n--- REGISTRO DE PROVEEDOR ---");
        System.out.print("Nombre del proveedor: ");
        String name = System.console().readLine();
        
        System.out.print("Dirección: ");
        String address = System.console().readLine();
        
        System.out.print("Teléfono: ");
        String cellphone = System.console().readLine();
        
        System.out.print("Email: ");
        String email = System.console().readLine();
        
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
        return Integer.parseInt(System.console().readLine());
    }

    public void displaySuccessMessage(String message) {
        System.out.println("\nÉXITO: " + message);
    }

    public void displayErrorMessage(String message) {
        System.out.println("\nERROR: " + message);
    }
}