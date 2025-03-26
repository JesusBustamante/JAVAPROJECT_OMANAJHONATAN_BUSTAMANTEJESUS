package Model;

import java.time.LocalDate;

public class Vaccines {
    
    private int id;
    private String name;
    private String type;
    private String manufacturer;
    private String pack;
    private LocalDate applyDate;
    private LocalDate nextApplyDate;
    private LocalDate expirationDate;

    public Vaccines(int id, String name, String type, String manufacturer, String pack, LocalDate applyDate, LocalDate nextApplyDate, LocalDate expirationDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.manufacturer = manufacturer;
        this.pack = pack;
        this.applyDate = applyDate;
        this.nextApplyDate = nextApplyDate;
        this.expirationDate = expirationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public LocalDate getNextApplyDate() {
        return nextApplyDate;
    }

    public void setNextApplyDate(LocalDate nextApplyDate) {
        this.nextApplyDate = nextApplyDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    
}
