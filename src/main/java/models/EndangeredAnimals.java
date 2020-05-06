package models;

public class EndangeredAnimals extends Animals{

    public int health;

    public EndangeredAnimals(String species, int health, String location) {
        this.species = species;
        this.type = "Endangered";
        this.health = health;
        this.location = location;
    }
}
