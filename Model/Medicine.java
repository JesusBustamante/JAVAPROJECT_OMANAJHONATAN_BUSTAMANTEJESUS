package Model;

import java.util.Date;

public class Medicine {

    private int id, stock, supplierId;
    private String name, type, manufacturer;
    private Date expirationDate;

    public Medicine(int id, String name, String type, String manufacturer, int stock, Date expirationDate, int supplierId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.manufacturer = manufacturer;
        this.stock = stock;
        this.expirationDate = expirationDate;
        this.supplierId = supplierId;
    }

    public Medicine(String name, String type, String manufacturer, int stock, Date expirationDate, int supplierId) {
        this(0, name, type, manufacturer, stock, expirationDate, supplierId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    
    
    public boolean isExpired() {
        return expirationDate.before(new Date());
    }

    public boolean isLowStock() {
        return stock < 10;
    }
}
