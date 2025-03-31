package Model;

import java.util.Date;

public class Adoption {
    private int id;
    private Date date;
    private String agreement;
    private int petAdoptedId;
    private int adopterId;

    public Adoption(int id, Date date, String agreement, int petAdoptedId, int adopterId) {
        this.id = id;
        this.date = date;
        this.agreement = agreement;
        this.petAdoptedId = petAdoptedId;
        this.adopterId = adopterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public int getPetAdoptedId() {
        return petAdoptedId;
    }

    public void setPetAdoptedId(int petAdoptedId) {
        this.petAdoptedId = petAdoptedId;
    }

    public int getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(int adopterId) {
        this.adopterId = adopterId;
    }
}
