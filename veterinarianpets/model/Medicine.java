/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.veterinarianpets.model;

/**
 *
 * @author Juliany
 */


import java.util.Date;

public class Medicine {
    private int id, stock, supplierId;
    private String name, type, manufacturer;
    private Date expirationDate;
    
    public Medicine(String name, String type, String manufacturer, int stock, Date expirationDate, int supplierId) {
        this.name = name;
        this.type = type;
        this.manufacturer = manufacturer;
        this.stock = stock;
        this.expirationDate = expirationDate;
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

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
    
    
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getStock() { return stock; }
    public Date getExpirationDate() { return expirationDate; }
    public int getSupplierId() { return supplierId; }

    
}
    

