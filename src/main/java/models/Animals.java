package models;

import org.sql2o.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Animals {

//    Timer
    public Timer timer;

//    Constants
    public static final String aType = "Not Endangered";
    public static final int MAX_AGE = 10;

    public int id;
    public String species;
    public String type;
    public int age;
    public Timestamp lastseen;
    public String location;
    public int rangerId;

    public Animals(String species, int age, String location, int rangerId){
        this.species = species;
        this.type = aType;
        this.age = age;
        this.location = location;
        this.rangerId = rangerId;
        this.timer = new Timer();
    }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public int getAge() {
        return age;
    }

    public Timestamp getLastseen() {
        return lastseen;
    }

    public String getLocation() {
        return location;
    }

    public int getRangerId() {
        return rangerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animals animals = (Animals) o;
        return  id == animals.id &&
                age == animals.age &&
                rangerId == animals.rangerId &&
                timer.equals(animals.timer) &&
                species.equals(animals.species) &&
                type.equals(animals.type) &&
                lastseen.equals(animals.lastseen) &&
                location.equals(animals.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timer, id, species, type, age, lastseen, location, rangerId);
    }

    //    Class Methods
    public void aging() {
        if ( this.age > MAX_AGE){
            throw new UnsupportedOperationException("Cannot age any more!");
        }
        for (int i = this.getAge(); i < MAX_AGE; i += 1){
            this.age += 1;
        }
    }

    public boolean isAlive() {
        if (this.getAge() >= 10){
            return false;
        }
        return true;
    }

    public void startAging() {
        Animals animal = this;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(!(isAlive())){
                    cancel();
                }
                aging();
            }
        };
        this.timer.schedule(timerTask, 1000, 500);
    }


//    Database Methods
    public void save(){
        String sql = "INSERT INTO animals (species, type, age, lastseen, location, rangerId) VALUES (:species, :type, :age, now(), :location, :rangerId);";
        try (Connection conn = DB.sql2o.open()){
            this.id = (int)conn.createQuery(sql, true)
                    .addParameter("species", this.getSpecies())
                    .addParameter("type", this.getType())
                    .addParameter("age", this.getAge())
                    .addParameter("location", this.getLocation())
                    .addParameter("rangerId", this.getRangerId())
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Animals> all() {
        String sql = "SELECT * FROM animals WHERE type = 'Not Endangered';";
        try (Connection conn = DB.sql2o.open()){
            return conn.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animals.class);
        }
    }

    public static Animals find(int id) {
        String sql = "SELECT * FROM animals WHERE id = :id AND type = 'Not Endangered';";
        try(Connection conn = DB.sql2o.open()) {
            Animals foundAnimal = conn.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Animals.class);
            return foundAnimal;
        }
    }

    public void update(int id, String species, int age, String location, int rangerId) {
        String sql = "UPDATE animals SET (species, age, lastseen, location, rangerId) = (:species, :age, now(), :location, :rangerId) WHERE id = :id;";
        try (Connection conn = DB.sql2o.open()) {
            conn.createQuery(sql, true)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .addParameter("species", species)
                    .addParameter("age", age)
                    .addParameter("location", location)
                    .addParameter("rangerId", rangerId)
                    .executeUpdate();
        }
    }

    public static void deleteById(int id){
        String sql = "DELETE FROM animals WHERE id = :id;";
        try(Connection conn = DB.sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public static void deleteAll() {
        String sql = "DELETE FROM animals *;";
        try (Connection conn = DB.sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }
    }
}
