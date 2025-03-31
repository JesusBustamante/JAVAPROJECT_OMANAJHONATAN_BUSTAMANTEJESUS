package Model;

import java.util.Date;

public class MedicalConsultation {
    
    private int id;
    private Date date;
    private int petId;
    private int ownerId;
    private String reason;
    private String status; // Programada, En proceso, Finalizada, Cancelada
    private String services;
    private String haircutType;
    private String observations;
    private String specialConditions;
    private CustomizedPlan customizedPlan;

    public MedicalConsultation(int id, Date date, int petId, int ownerId, String reason, String status, String services, String haircutType, String observations, String specialConditions, CustomizedPlan customizedPlan) {
        this.id = id;
        this.date = date;
        this.petId = petId;
        this.ownerId = ownerId;
        this.reason = reason;
        this.status = status;
        this.services = services;
        this.haircutType = haircutType;
        this.observations = observations;
        this.specialConditions = specialConditions;
        this.customizedPlan = customizedPlan;
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

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getHaircutType() {
        return haircutType;
    }

    public void setHaircutType(String haircutType) {
        this.haircutType = haircutType;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public CustomizedPlan getCustomizedPlan() {
        return customizedPlan;
    }

    public void setCustomizedPlan(CustomizedPlan customizedPlan) {
        this.customizedPlan = customizedPlan;
    }

}
