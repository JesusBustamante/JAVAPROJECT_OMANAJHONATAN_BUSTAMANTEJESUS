package Model;

import java.util.Date;

public class Supply {
    private int id, stock, supplierId;
    private String type, pack;
    private Date entryDate, expirationDate;
    
    public Supply(String type, String pack, int stock, Date entryDate, Date expirationDate, int supplierId) {
        this.type = type;
        this.pack = pack;
        this.stock = stock;
        this.entryDate = entryDate;
        this.expirationDate = expirationDate;
        this.supplierId = supplierId;
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
    
    
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getStock() { return stock; }
    public Date getExpirationDate() { return expirationDate; }
    public int getSupplierId() { return supplierId; }
}

