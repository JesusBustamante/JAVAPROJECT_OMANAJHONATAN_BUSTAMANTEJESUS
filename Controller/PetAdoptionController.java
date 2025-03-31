package Controller;

import Model.Adopter;
import Model.AdopterDAO;
import Model.AdoptionDAO;
import Model.PetAdoption;
import Model.PetAdoptionDAO;
import View.PetAdoptionView;

import java.util.List;

import java.util.List;

public class PetAdoptionController {
    private PetAdoptionDAO petDAO;
    private AdopterDAO adopterDAO;
    private AdoptionDAO adoptionDAO;
    private PetAdoptionView view;

    public PetAdoptionController() {
        this.petDAO = new PetAdoptionDAO();
        this.adopterDAO = new AdopterDAO();
        this.adoptionDAO = new AdoptionDAO();
        this.view = new PetAdoptionView();
    }

    public void startAdoptionModule() {
        boolean running = true;
        while (running) {
            view.showMenu();
            int option = view.getInputInt("Seleccione una opción:");

            switch (option) {
                case 1:
                    addPet();
                    break;
                case 2:
                    listPets();
                    break;
                case 3:
                    registerAdopter();
                    break;
                case 4:
                    adoptPet();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    view.showMessage("Opción inválida. Intente nuevamente.");
            }
        }
    }

    public void addPet() {
        String name = view.getInputString("Ingrese el nombre de la mascota:");
        String species = view.getInputString("Ingrese la especie:");
        String race = view.getInputString("Ingrese la raza:");
        int age = view.getInputInt("Ingrese la edad:");
        String gender = view.getInputString("Ingrese el género (M/F):");
        String description = view.getInputString("Ingrese una descripción:");
        String photo = view.getInputString("Ingrese la URL de la foto:");

        PetAdoption pet = new PetAdoption(name, species, race, age, null, gender, description, photo);
        if (petDAO.addPet(pet)) {
            view.showMessage("Mascota registrada con éxito.");
        } else {
            view.showMessage("Error al registrar la mascota.");
        }
    }

    public void listPets() {
        List<PetAdoption> pets = petDAO.getAllPets();
        view.showPets(pets);
    }

    public void registerAdopter() {
        Adopter adopter = view.getAdopterDetails();
        if (adopterDAO.addAdopter(adopter)) {
            view.showMessage("Adoptante registrado con éxito.");
        } else {
            view.showMessage("Error al registrar adoptante.");
        }
    }

    public void adoptPet() {
        listPets();
        int petId = view.getPetIdForAdoption();
        int adopterId = view.getAdopterId();

        if (adoptionDAO.registerAdoption(adopterId, petId)) {
            view.confirmAdoptionSuccess();
        } else {
            view.confirmAdoptionFailure();
        }
    }
}
