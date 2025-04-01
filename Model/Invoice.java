package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    
    private int id;
    private Date date;
    private double subtotal;
    private double taxes;
    private double total;
    private String CUFE;
    private String QR;
    private List <AdittionalService> adittionalService;
    
    public Invoice(int id, Date date, double subtotal, double taxes, double total, String CUFE, String QR, List<AdittionalService> adittionalServices) {
        this.id = id;
        this.date = date;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.total = total;
        this.CUFE = CUFE;
        this.QR = QR;
        this.adittionalService = new ArrayList<>();
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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCUFE() {
        return CUFE;
    }

    public void setCUFE(String CUFE) {
        this.CUFE = CUFE;
    }

    public String getQR() {
        return QR;
    }

    public void setQR(String QR) {
        this.QR = QR;
    }

    public List<AdittionalService> getAdittionalService() {
        return adittionalService;
    }

    public void setAdittionalService(List<AdittionalService> adittionalService) {
        this.adittionalService = adittionalService;
    }
}
