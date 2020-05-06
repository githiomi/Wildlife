package models;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class EndangeredAnimalsTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void canInstantiateANewEAnimal(){
        EndangeredAnimals e1 = e1();
        assertEquals(false, e1 instanceof EndangeredAnimals);
    }


//    Helper classes
    public static EndangeredAnimals e1() {
        return new EndangeredAnimals("Rhino", 4, 7, "Zone 1", 1);
    }

    public static EndangeredAnimals e2() {
        return new EndangeredAnimals("Elephant", 5, 8, "Near River", 1);
    }

}