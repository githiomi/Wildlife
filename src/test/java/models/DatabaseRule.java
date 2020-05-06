package models;
import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

    @Override
    protected void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tests", "dhosio", "MaFaD@niel2019");
    }

    @Override
    protected void after() {
        try(Connection con = DB.sql2o.open()) {
            String deleteRangersQuery = "DELETE FROM rangers *;";
            String deleteAnimalsQuery = "DELETE FROM animals *;";
            con.createQuery(deleteRangersQuery).executeUpdate();
            con.createQuery(deleteAnimalsQuery).executeUpdate();
        }
    }

}