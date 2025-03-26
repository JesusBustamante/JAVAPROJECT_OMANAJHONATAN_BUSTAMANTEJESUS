package Model;

import java.util.List;
import java.util.Map;

public class HealthHistorial {
    
    private int id;
    private Pet pet;
    private double weight;
    private Map<String, Double> sizes;
    private List<Vaccines> vaccines;
    private List<String> allergies;
    private List<PreExistingConditions> preExistingConditions;
    private List<ProcedurePerformed> proceduresPerformed;

    public HealthHistorial(int id, Pet pet, double weight, Map<String, Double> sizes, List<Vaccines> vaccines, List<String> allergies, List<PreExistingConditions> preExistingConditions, List<ProcedurePerformed> proceduresPerformed) {
        this.id = id;
        this.pet = pet;
        this.weight = weight;
        this.sizes = sizes;
        this.vaccines = vaccines;
        this.allergies = allergies;
        this.preExistingConditions = preExistingConditions;
        this.proceduresPerformed = proceduresPerformed;
    }

    public HealthHistorial(int id, Pet pet) {
        this.id = id;
        this.pet = pet;
    }

    public HealthHistorial(int id, Pet pet, List<ProcedurePerformed> proceduresPerformed) {
        this.id = id;
        this.pet = pet;
        this.proceduresPerformed = proceduresPerformed;
    }

    public HealthHistorial(int id, Pet pet, List<Vaccines> vaccines, List<ProcedurePerformed> proceduresPerformed) {
        this.id = id;
        this.pet = pet;
        this.vaccines = vaccines;
        this.proceduresPerformed = proceduresPerformed;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Map<String, Double> getSizes() {
        return sizes;
    }

    public void setSizes(Map<String, Double> sizes) {
        this.sizes = sizes;
    }

    public List<Vaccines> getVaccines() {
        return vaccines;
    }

    public void setVaccines(List<Vaccines> vaccines) {
        this.vaccines = vaccines;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<PreExistingConditions> getPreExistingConditions() {
        return preExistingConditions;
    }

    public void setPreExistingConditions(List<PreExistingConditions> preExistingConditions) {
        this.preExistingConditions = preExistingConditions;
    }

    public List<ProcedurePerformed> getProceduresPerformed() {
        return proceduresPerformed;
    }

    public void setProceduresPerformed(List<ProcedurePerformed> proceduresPerformed) {
        this.proceduresPerformed = proceduresPerformed;
    }
    
}
