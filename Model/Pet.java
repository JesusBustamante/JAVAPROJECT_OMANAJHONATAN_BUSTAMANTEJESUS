package Model;

import java.time.LocalDate;

public class Pet {
    
    private int id;
    private String name;
    private String species;
    private String race;
    private int age;
    private LocalDate birth_date;
    private Gender gender;
    private HealthHistorial health_historial;
    private CustomizedPlan customized_plan;
    private String microchip;
    private Boolean tattoo;
    private String photo_url;
    private Owner owner;

    public Pet(int id, String name, String species, String race, int age, LocalDate birth_date, Gender gender, HealthHistorial health_historial, CustomizedPlan customized_plan, String microchip, Boolean tattoo, String photo_url, Owner owner) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.race = race;
        this.age = age;
        this.birth_date = birth_date;
        this.gender = gender;
        this.health_historial = health_historial;
        this.customized_plan = customized_plan;
        this.microchip = microchip;
        this.tattoo = tattoo;
        this.photo_url = photo_url;
        this.owner = owner;
    }

    public Pet(String name, String species, String race, int age, LocalDate birth_date, Gender gender, String microchip, Boolean tattoo, String photo_url, Owner owner) {
        this.name = name;
        this.species = species;
        this.race = race;
        this.age = age;
        this.birth_date = birth_date;
        this.gender = gender;
        this.microchip = microchip;
        this.tattoo = tattoo;
        this.photo_url = photo_url;
        this.owner = owner;
    }

    public Pet(int id, String name, String species, String race, int age, LocalDate birth_date, Gender gender, String microchip, Boolean tattoo, String photo_url, Owner owner) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.race = race;
        this.age = age;
        this.birth_date = birth_date;
        this.gender = gender;
        this.microchip = microchip;
        this.tattoo = tattoo;
        this.photo_url = photo_url;
        this.owner = owner;
    }

    public Pet(int id, String name) {
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public HealthHistorial getHealth_historial() {
        return health_historial;
    }

    public void setHealth_historial(HealthHistorial health_historial) {
        this.health_historial = health_historial;
    }

    public CustomizedPlan getCustomized_plan() {
        return customized_plan;
    }

    public void setCustomized_plan(CustomizedPlan customized_plan) {
        this.customized_plan = customized_plan;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public Boolean getTattoo() {
        return tattoo;
    }

    public void setTattoo(Boolean tattoo) {
        this.tattoo = tattoo;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    
    
    
}

