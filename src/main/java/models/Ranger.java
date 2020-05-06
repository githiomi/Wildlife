package models;

import org.sql2o.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ranger {

    private int id;
    private String name;
    private int badgeNumber;
    private int radioNumber;
    private String rank;

    public Ranger(String name, int badgeNumber, int radioNumber, String rank) {
        this.name = name;
        this.badgeNumber = badgeNumber;
        this.radioNumber = radioNumber;
        this.rank = rank;
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

    public int getBadgeNumber() {
        return badgeNumber;
    }

    public int getRadioNumber() {
        return radioNumber;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ranger ranger = (Ranger) o;
        return id == ranger.id &&
                badgeNumber == ranger.badgeNumber &&
                radioNumber == ranger.radioNumber &&
                name.equals(ranger.name) &&
                rank.equals(ranger.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, badgeNumber, radioNumber, rank);
    }

    public void save() {
        String sql = "INSERT INTO rangers (name, badgeNumber, radioNumber, rank) VALUES (:name, :badgeNumber, :radioNumber, :rank);";
        try (Connection conn = DB.sql2o.open()){
            this.id = (int) conn.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("badgeNumber", this.badgeNumber)
                    .addParameter("radioNumber", this.radioNumber)
                    .addParameter("rank", this.rank)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Ranger> all() {
        String sql = "SELECT * FROM rangers;";
        try (Connection conn = DB.sql2o.open()){
            return conn.createQuery(sql)
                    .executeAndFetch(Ranger.class);
        }
    }

    public static Ranger find(int id){
        String sql = "SELECT * FROM rangers WHERE id=:id";
        try (Connection conn = DB.sql2o.open()) {
            Ranger ranger = conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Ranger.class);
            return ranger;
        }
    }

    public static void deleteById(int id){
        String sql = "DELETE FROM rangers WHERE id = :id;";
        try (Connection conn = DB.sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public static void deleteAll() {
        String sql = "DELETE FROM rangers *;";
        try (Connection conn = DB.sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }
    }

    public List<Object> getAllAnimals() {
        List<Object> allAnimals = new ArrayList<>();

        try (Connection conn = DB.sql2o.open()){

            String animalsSql = "SELECT * FROM animals WHERE rangerId = :rangerId AND type = 'Not Endangered';";
            List<Animals> animals = conn.createQuery(animalsSql)
                        .throwOnMappingFailure(false)
                        .addParameter("rangerId", this.id)
                        .executeAndFetch(Animals.class);
            allAnimals.addAll(animals);

            String eAnimalSql = "SELECT * FROM animals WHERE rangerId = :id;";
            List<EndangeredAnimals> eAnimals = conn.createQuery(eAnimalSql)
                    .throwOnMappingFailure(false)
                    .addParameter("id", this.getId())
                    .executeAndFetch(EndangeredAnimals.class);
            allAnimals.addAll(eAnimals);
        }
        return allAnimals;
    }

}
