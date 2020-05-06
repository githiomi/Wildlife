package models;

import org.junit.Rule;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class AnimalsTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void canCreateAnInstanceOfAnimals() {
        Animals a1 = a1();
        assertTrue( a1 instanceof Animals);
    }

//    @Test
//    public void returnsTrueIfAnimalsAreTheSame() {
//        Animals a1 = a1();
//        Animals a2 = a1();
//        assertTrue(a1.equals(a2));
//    }
//
//    @Test
//    public void ensuresThatSavedIsTheSameAsLocal(){
//        Animals a1 = a1();
//            a1.save();
//        assertTrue(Animals.find(a1.getId()).equals(a1));
//    }

    @Test
    public void canCountAllAnimals(){
        Animals a1 = a1();
        Animals a2 = a2();
            a1.save();
            a2.save();
        int length = Animals.all().size();
        assertEquals(2, length);
    }

    @Test
    public void aSingleAnimalCanBeFound() {
        Animals a1 = a1();
        Animals a2 = a2();
            a1.save();
            a2.save();
        Animals found = Animals.find(a1.getId());
        assertEquals("Lion", found.getSpecies());
    }

    @Test
    public void userCanUpdateTheAnimal() {
        Animals a1 = a1();
            a1.save();
            a1.update(a1.getId(), a2().getSpecies(), a2().getAge(), a2().getLocation(), a2().getRangerId());
        Animals updated = Animals.find(a1.getId());
            assertEquals("Gazelle", updated.getSpecies());
            assertEquals(4, updated.getAge());
            assertEquals(2, updated.getRangerId());
        Timestamp now = new Timestamp(new Date().getTime());
            assertEquals(DateFormat.getDateTimeInstance().format(now), DateFormat.getDateTimeInstance().format(Animals.find(a1.getId()).getLastseen()));
    }

    @Test
    public void userCanDeleteAnAnimal()  {
        Animals a1 = a1();
        Animals a2 = a2();
        a1.save();
        a2.save();
        int fLength = Animals.all().size();
            Animals.deleteById(a1.getId());
        int lLength = Animals.all().size();
        assertTrue(fLength > lLength);
        assertEquals(2, fLength);
        assertEquals(1, lLength);
    }

    @Test
    public void userCanDeleteAllAnimalsInDatabase() {
        Animals a1 = a1();
        Animals a2 = a2();
        a1.save();
        a2.save();
        Animals.deleteAll();
        int length = Animals.all().size();
        assertEquals(0, length);
    }

    @Test
    public void checksIfAnimalIsAlive(){
        Animals a1 = a1();
            a1.save();
        assertEquals(true, a1.isAlive());
    }

    @Test
    public void theAgeOfTheAnimalIncreases() {
        Animals a2 = a2();
            a2().save();
        int fAge = a2.getAge();
            a2.aging();
            try{
                Thread.sleep(2000);
            }catch (InterruptedException ex){

            }
        int lAge = a2.getAge();
        assertTrue(lAge > fAge);
    }

    @Test
    public void timerHaltsWhenAgeIsAtMax() {
        Animals a1 = a1();
            a1.save();
        a1.startAging();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException ex){
            System.out.println(ex);
        }
        assertFalse(a1.isAlive());
        assertTrue( a1.getAge() == 10);
    }



//    Helper Classes
    public static Animals a1() {
        return new Animals("Lion", 6, "Zone 1", 1);
    }

    public static Animals a2() {
        return new Animals("Gazelle", 4, "Near River", 2);
    }

}