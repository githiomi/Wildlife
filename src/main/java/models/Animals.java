package models;

import org.sql2o.*;

import java.sql.Timestamp;
import java.util.List;

public class Animals {

    public static final String aType = "Not Endangered";

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
        String sql = "SELECT * FROM animals WHERE id = :id;";
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
