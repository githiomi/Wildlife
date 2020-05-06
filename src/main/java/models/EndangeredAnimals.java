package models;

import org.sql2o.*;

import java.util.List;
import java.util.TimerTask;

public class EndangeredAnimals extends Animals{

    public int health;
    public static final String eType = "Not Endangered";
    public static final int MIN_HEALTH = 0;

    public EndangeredAnimals(String species, int health, int age, String location, int rangerId) {
        super(species, age, location, rangerId);
        this.type = eType;
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

//    Class Methods
    public void depleting() {
        if (isAlive()) {
            for (int i = this.getHealth(); i > MIN_HEALTH; i -= 1) {
                health -= 1;
            }
        }
    }

    public void healthDeplete() {
        EndangeredAnimals endangeredAnimals = this;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!(isAlive())){
                    cancel();
                }
                depleting();
            }
        };
        this.timer.schedule(timerTask, 0, 500);
    }

    @Override
    public boolean isAlive(){
        if (this.isAlive() && health > 0){
            return true;
        }
        return false;
    }



    //    Database Methods
    @Override
    public void save(){
        String sql = "INSERT INTO animals (species, type, health, age, lastseen, location, rangerId) VALUES (:species, :type, :health, :age, now(), :location, :rangerId);";
        try (Connection conn = DB.sql2o.open()){
            this.id = (int)conn.createQuery(sql, true)
                    .addParameter("species", this.getSpecies())
                    .addParameter("type", this.getType())
                    .addParameter("health", this.getHealth())
                    .addParameter("age", this.getAge())
                    .addParameter("location", this.getLocation())
                    .addParameter("rangerId", this.getRangerId())
                    .executeUpdate()
                    .getKey();
        }
    }

//    @Override
    public static List<EndangeredAnimals> getAll() {
        String sql = "SELECT * FROM animals WHERE type = 'Endangered';";
        try (Connection conn = DB.sql2o.open()){
            return conn.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(EndangeredAnimals.class);
        }
    }


    public static EndangeredAnimals find(int id) {
        String sql = "SELECT * FROM animals WHERE id = :id;";
        try(Connection conn = DB.sql2o.open()) {
            EndangeredAnimals foundAnimal = conn.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .executeAndFetchFirst(EndangeredAnimals.class);
            return foundAnimal;
        }
    }


    public void update(int id, String species, int health, int age, String location, int rangerId) {
        String sql = "UPDATE animals SET (species, health, age, lastseen, location, rangerId) = (:species, :health, :age, now(), :location, :rangerId) WHERE id = :id;";
        try (Connection conn = DB.sql2o.open()) {
            conn.createQuery(sql, true)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .addParameter("species", species)
                    .addParameter("health", health)
                    .addParameter("age", age)
                    .addParameter("location", location)
                    .addParameter("rangerId", rangerId)
                    .executeUpdate();
        }
    }

}
