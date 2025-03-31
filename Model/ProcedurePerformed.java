package Model;

import java.time.LocalDate;

public class ProcedurePerformed {
    
    private int id;
    private String name;
    private String description;
    private LocalDate datePerformed;
    private String type;
    private String preoperativeReport;

    public ProcedurePerformed(int id, String name, String description, LocalDate datePerformed, String type, String preoperativeReport) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.datePerformed = datePerformed;
        this.type = type;
        this.preoperativeReport = preoperativeReport;
    }

    public ProcedurePerformed(int id, String name) {
        this.id = id;
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPreoperativeReport() {
        return preoperativeReport;
    }

    public void setPreoperativeReport(String preoperativeReport) {
        this.preoperativeReport = preoperativeReport;
    }

    
}
