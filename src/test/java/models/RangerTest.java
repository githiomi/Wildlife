package models;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class RangerTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();


    @Test
    public void getName_personInstantiatesWithName_Henry() {
        Ranger r1 = r2();
        assertEquals("Dhosio", r1.getName());
    }

    @Test
    public void getEmail_personInstantiatesWithEmail_String() {
        Ranger r1 = r1();
        assertEquals("Daniel", r1.getName());
    }

    @Test
    public void returnsTrueIfSame() {
        Ranger r1 = r1();
        Ranger r2 = r1();
        assertTrue(r1.equals(r2));
    }

    @Test
    public void save_insertsRangerIntoDatabase_Person() {
        Ranger r1 = r1();
        r1.save();
        assertTrue(Ranger.all().get(0).equals(r1));
    }

    @Test
    public void save_assignsIdToObject() {
        Ranger r1 = r1();
            r1.save();
        Ranger saved = Ranger.all().get(0);
        assertEquals(r1.getId(), saved.getId());
    }

    @Test
    public void all_returnsAllInstancesOfRanger_true() {
        Ranger r1 = r1();
            r1.save();
        Ranger r2 = r2();
            r2.save();

        assertEquals(true, Ranger.all().get(0).equals(r1));
        assertEquals(true, Ranger.all().get(1).equals(r2));
    }

    @Test
    public void returnsPersonWithSameId() {
        Ranger r1 = r1();
        Ranger r2 = r2();
            r1.save();
            r2.save();
        assertEquals(Ranger.find(r2.getId()), r2);
    }

//    Helper classes
    public static Ranger r1() {
        return new Ranger("Daniel", 1234, 5678, "Colonel");
    }

    public static Ranger r2() {
        return new Ranger ("Dhosio", 4321, 8765, "Lieutenant");
    }
}
