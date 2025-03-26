package Controller;

//import Model.HealthHistorialDAO;
import Model.Owner;
import Model.OwnerDAO;
import Model.Pet;
import Model.PetDAO;
import View.HealthHistorialView;
import View.OwnerView;
import View.PetView;
import java.util.List;
import java.util.Scanner;

public class OwnerPetController {

    private OwnerDAO ownerDAO;
    private PetDAO petDAO;
    private OwnerView ownerView;
    private PetView petView;
    private Scanner scanner;

    public OwnerPetController(OwnerDAO ownerDAO, PetDAO petDAO, OwnerView ownerView, PetView petView) {
        this.ownerDAO = ownerDAO;
        this.petDAO = petDAO;
        this.ownerView = ownerView;
        this.petView = petView;
        this.scanner = new Scanner(System.in);
    }

    public void OwnerPet() {

        while (true) {
            System.out.println("\n==== OWNERS AND PETS REGISTRATION MODULE ====\n");
            System.out.println("1. Register owner and pet");
            System.out.println("2. Register new pet to an existing owner");
            System.out.println("3. List owners and pets");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registerOwnerYPet();
                    break;
                case 2:
                    registerNewPet();
                    break;
                case 3:
                    listarOwnersYPets();
                    break;
                case 4 :
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void registerOwnerYPet() {
        // Registrar dueño y obtener el ID generado
        Owner owner = ownerView.agregarOwner();
        int ownerId = ownerDAO.insertar(owner);

        if (ownerId == -1) {
            System.out.println("\nError al registrar el dueño.");
            return;
        }

        owner.setId(ownerId); // Asignamos el ID correcto al objeto Owner

        // Registrar mascota con el dueño correcto
        Pet pet = petView.agregarPet(owner);
        petDAO.insertar(pet);

        System.out.println("\nDueño y mascota registrados exitosamente.");
    }

    public void registerNewPet() {
        System.out.print("Ingrese la cédula (personalId) del dueño: ");
        int personalId = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        Owner owner = ownerDAO.buscarPorPersonalId(personalId);

        if (owner == null) {
            System.out.println("❌ Error: No existe un dueño con esta cédula.");
            return;
        }

        // Si el dueño existe, permitir registrar una nueva mascota
        Pet pet = petView.agregarPet(owner);
        petDAO.insertar(pet);

        System.out.println("✔ Nueva mascota " + pet.getId() + " - " + pet.getName() + " registrada para el dueño " + owner.getFull_name());
    }

    private void listarOwnersYPets() {
        List<Owner> owners = ownerDAO.obtenerTodos();
        ownerView.mostrarOwners(owners);
    }

}
