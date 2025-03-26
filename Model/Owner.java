package Model;

import java.util.ArrayList;
import java.util.List;

public class Owner {
    private int id;
    private int personal_id;
    private String NIT;
    private String fullName;
    private String address;
    private String cellphone;
    private String email;
    private String emergencyContact;
    private List<Pet> pets;

    public Owner(int id, int personal_id, String NIT, String fullName, String address, String cellphone, String email, String emergencyContact, List<Pet> pets) {
        this.id = id;
        this.personal_id = personal_id;
        this.NIT = NIT;
        this.fullName = fullName;
        this.address = address;
        this.cellphone = cellphone;
        this.email = email;
        this.emergencyContact = emergencyContact;
        this.pets = new ArrayList<>();
    }

    public Owner(int personal_id, String NIT, String fullName, String address, String cellphone, String email, String emergencyContact) {
        this.personal_id = personal_id;
        this.NIT = NIT;
        this.fullName = fullName;
        this.address = address;
        this.cellphone = cellphone;
        this.email = email;
        this.emergencyContact = emergencyContact;
    }

    public Owner(int id, int personal_id, String NIT, String fullName, String address, String cellphone, String email, String emergencyContact) {
        this.id = id;
        this.personal_id = personal_id;
        this.NIT = NIT;
        this.fullName = fullName;
        this.address = address;
        this.cellphone = cellphone;
        this.email = email;
        this.emergencyContact = emergencyContact;
    }
    
        // Método para agregar una mascota
    public void addPet(Pet pet) {
        if (this.pets == null) { // Verifica que la lista esté inicializada
            this.pets = new ArrayList<>();
        }
        this.pets.add(pet);
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonal_id() {
        return personal_id;
    }

    public void setPersonal_id(int personal_id) {
        this.personal_id = personal_id;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getFull_name() {
        return fullName;
    }

    public void n(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergency_contact() {
        return emergencyContact;
    }

    public void setEmergency_contact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
   
    // Getter para la lista de mascotas
    public List<Pet> getPets() {
        if (this.pets == null) { // 🔹 Asegurar que no retorne null
            this.pets = new ArrayList<>();
        }
        return pets;
    }
    
    public void setPets(List<Pet> pets) {
        this.pets = pets; 
    }
    
}
