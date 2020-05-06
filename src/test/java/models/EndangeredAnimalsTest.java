package models;

import org.junit.Rule;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class EndangeredAnimalsTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void canInstantiateANewEAnimal(){
        EndangeredAnimals e1 = e1();
        assertEquals(true, e1 instanceof EndangeredAnimals);
    }

    @Test
    public void checksIfSimilar() {
        EndangeredAnimals e1 = e1();
        EndangeredAnimals e2 = e1();
        e1.save();
        e2.save();
    }

    @Test
    public void checksIfEAnimalIsStored() {
        EndangeredAnimals e1 = e1();
            e1.save();
        EndangeredAnimals e2 = e2();
            e2.save();
        int length = EndangeredAnimals.getAll().size();
        assertEquals(2, length);
    }

    @Test
    public void aSingleEAnimalCanBeFound() {
        EndangeredAnimals e1 = e1();
            e1.save();
            String species = "Rhino";
        assertEquals(species, EndangeredAnimals.find(e1.getId()).getSpecies());
    }

    @Test
    public void canUpdateTheEAnimals(){
        EndangeredAnimals e1 = e1();
            e1.save();
        e1.update(e1.getId(), e2().species, e2().health, e2().getAge(), e2().location, e2().rangerId);
        EndangeredAnimals retrieved = EndangeredAnimals.find(e1.getId());
        assertEquals("Elephant", retrieved.getSpecies());
    }

    @Test
    public void userCanDeleteTheSaidEAnimal() {
        EndangeredAnimals e1 = e1();
        EndangeredAnimals e2 = e2();
            e1.save();
            e2.save();
        int fLength = EndangeredAnimals.getAll().size();
            EndangeredAnimals.deleteById(e2.getId());
        int lLength = EndangeredAnimals.getAll().size();
        assertEquals(2, fLength);
        assertEquals(1, lLength);
        assertTrue(fLength > lLength);
    }

    @Test
    public void userCanDeleteAllEAnimals() {
        EndangeredAnimals e1 = e1();
        EndangeredAnimals e2 = e2();
        e1.save();
        e2.save();
        int fLength = EndangeredAnimals.getAll().size();
            EndangeredAnimals.deleteAll();
        int lLength = EndangeredAnimals.getAll().size();
        assertTrue(lLength == 0);
        assertTrue(fLength > lLength);
        assertEquals(0, lLength);
    }

    @Test
    public void getTheTimestampForCreation(){
        EndangeredAnimals e1 = e1();
            e1.save();
        Timestamp lastseen = EndangeredAnimals.find(e1.getId()).getLastseen();
        Timestamp now = new Timestamp(new Date().getTime());
        assertEquals(DateFormat.getDateTimeInstance().format(lastseen.getDay()), DateFormat.getDateTimeInstance().format(now.getDay()));
    }


//    Helper classes
    public static EndangeredAnimals e1() {
        return new EndangeredAnimals("Rhino", 4, 7, "Zone 1", 1);
    }

    public static EndangeredAnimals e2() {
        return new EndangeredAnimals("Elephant", 5, 8, "Near River", 1);
    }

}