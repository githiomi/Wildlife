package models;

public class EndangeredAnimals extends Animals{

    public int health;
    public static final String eType = "Not Endangered";

    public EndangeredAnimals(String species, int age, String location, int rangerId, int health) {
        super(species, age, location, rangerId);
        this.type = eType;
        this.health = health;
    }


}
