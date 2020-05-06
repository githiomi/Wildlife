package models;

import org.sql2o.*;

import java.util.ArrayList;
import java.util.List;

public class Location{

    private int id;
    private String name;
    private String area;

    public Location(String name, String area) {
        this.name = name;
        this.area = area;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public static List<Location> getAll() {
        String sql = "SELECT * FROM locations;";
        try (Connection conn = DB.sql2o.open()){
             return conn.createQuery(sql)
                    .executeAndFetch(Location.class);
        }
    }

    public static Location find(int id){
        String sql = "SELECT * FROM locations WHERE id =:id;";
        try (Connection conn = DB.sql2o.open()){
            Location location = conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Location.class);
            return location;
        }
    }
}

