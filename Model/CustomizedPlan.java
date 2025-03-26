package Model;

import java.util.List;

public class CustomizedPlan {
    
    private int id;
    private String name;
    private String description;
    private String race;
    private int behavior;
    private List<String> recommendations;
    private Pet pet;

    public CustomizedPlan(int id, String name, String description, String race, int behavior, List<String> recommendations, Pet pet) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.race = race;
        this.behavior = behavior;
        this.recommendations = recommendations;
        this.pet = pet;
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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getBehavior() {
        return behavior;
    }

    public void setBehavior(int behavior) {
        this.behavior = behavior;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
    
    
}
