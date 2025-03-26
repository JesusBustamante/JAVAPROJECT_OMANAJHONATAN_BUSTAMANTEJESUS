package Controller;

import Model.Veterinarian;
import Model.VeterianarianDAO;
import View.VeterinarianView;
import java.util.List;
import java.util.Scanner;

public class VeterinarianController {
    private Scanner sc;
    private VeterinarianView veterinarianView;
    private VeterianarianDAO veterinarianDAO;

    public VeterinarianController(VeterinarianView veterinarianView, VeterianarianDAO veterinarianDAO) {
        this.veterinarianView = veterinarianView;
        this.veterinarianDAO = veterinarianDAO;
        this.sc = new Scanner(System.in);
    }

    public void iniciarVeterinarian() {
        while (true) {
            System.out.println("\n=== MÓDULO DE VETERINARIOS ===");
            System.out.println("1. Registar veterinarios");
            System.out.println("2️. Listar veterinarios");
            System.out.println("3️. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    Veterinarian nuevoVeterinario = veterinarianView.agregarVeterinario();
                    veterinarianDAO.insertar(nuevoVeterinario);
                    break;
                case 2:
                    List<Veterinarian> veterinarians = veterinarianDAO.obtenerTodos();
                    veterinarianView.mostrarVeterinarios(veterinarians);
                    break;
                case 3:
                    System.out.println("🔙 Volviendo al menú principal...");
                    return;
                default:
                    System.out.println("❌ Opción no válida. Intente de nuevo.");
            }
        }
    }
}
