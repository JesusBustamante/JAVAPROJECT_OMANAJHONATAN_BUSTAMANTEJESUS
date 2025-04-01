package Model;

import java.util.Date;

public class Supply {

    private int id;
    private String type;
    private String pack;
    private Date entryDate;
    private Date expirationDate;
    private Supplier supplier;

    public Supply(int id, String type, String pack, Date entryDate, Date expirationDate, Supplier supplier) {
        this.id = id;
        this.type = type;
        this.pack = pack;
        this.entryDate = entryDate;
        this.expirationDate = expirationDate;
        this.supplier = supplier;
    }

    public Supply(String type, String pack, Date entryDate, Date expirationDate, Supplier supplier) {
        this.type = type;
        this.pack = pack;
        this.entryDate = entryDate;
        this.expirationDate = expirationDate;
        this.supplier = supplier;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public boolean isExpiringSoon() {
        Date today = new Date();
        long difference = expirationDate.getTime() - today.getTime();
        return difference <= (7 * 24 * 60 * 60 * 1000); 
    }
}
