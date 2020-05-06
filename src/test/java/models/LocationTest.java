package models;

import org.junit.Rule;
import org.junit.Test;
import org.sql2o.*;

import static org.junit.Assert.*;

public class LocationTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

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

    @Test
    public void canUseTheFindMethod() {
        Location location = Location.find(1);
        Location location1 = Location.find(2);
        assertEquals("Zone 1", location.getName());
        assertEquals("Green Zone", location1.getName());
    }
}