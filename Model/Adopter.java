package Model;

public class Adopter {

    private int id;
    private String personalId, fullName, address, cellphone, email, reference;

    public Adopter(String personalId, String fullName, String address, String cellphone,
            String email, String reference) {
        this.personalId = personalId;
        this.fullName = fullName;
        this.address = address;
        this.cellphone = cellphone;
        this.email = email;
        this.reference = reference;
    }

    public Adopter(int id, String personalId, String fullName, String address, String cellphone, String email, String reference) {
        this.id = id;
        this.personalId = personalId;
        this.fullName = fullName;
        this.address = address;
        this.cellphone = cellphone;
        this.email = email;
        this.reference = reference;
    }

    
    
    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
}
