/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.veterinarianpets.model;

/**
 *
 * @author Juliany
 */


public class Supplier {
    private int id;
    private String name, address, cellphone, email;
    
    public Supplier(String name, String address, String cellphone, String email) {
        this.name = name;
        this.address = address;
        this.cellphone = cellphone;
        this.email = email;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getCellphone() { return cellphone; }
    public String getEmail() { return email; }
}
    

