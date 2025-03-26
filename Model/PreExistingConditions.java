package Model;

import java.time.LocalDate;

public class PreExistingConditions {
    
    private int id;
    private String name;
    private String description;
    private LocalDate datePerformed;

    public PreExistingConditions(int id, String name, String description, LocalDate datePerformed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.datePerformed = datePerformed;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDatePerformed() {
        return datePerformed;
    }

    public void setDatePerformed(LocalDate datePerformed) {
        this.datePerformed = datePerformed;
    }
    
}
