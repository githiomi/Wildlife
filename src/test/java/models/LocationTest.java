package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.*;

import static org.junit.Assert.*;

public class LocationTest {

    @Before
    public void setUp() throws Exception {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tests", "dhosio", "MaFaD@niel2019");
    }

    @Test
    public void canGetASpecificLocation() {
        Location location = Location.getAll().get(0);
        String name = location.getName();
        assertEquals("Zone 1", name);
    }

    @Test
    public void canRetrieveAllLocationsFromDatabase() {
        int size = Location.getAll().size();
        assertTrue(4 == size);
    }

    @After
    public void tearDown() throws Exception {

    }
}