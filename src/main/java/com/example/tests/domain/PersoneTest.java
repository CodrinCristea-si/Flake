package com.example.tests.domain;

import com.example.Domain.Persone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersoneTest {

    @Test
    void getFirstName() {
        Persone pers= new Persone("Marius","Guga");
        assertTrue(pers.getFirstName().equals("Marius"));
    }

    @Test
    void setFirstName() {
        Persone pers= new Persone("Marius","Guga");
        pers.setFirstName("Bala");
        assertTrue(pers.getFirstName().equals("Bala"));
    }

    @Test
    void getLastName() {
        Persone pers= new Persone("Marius","Guga");
        assertTrue(pers.getLastName().equals("Guga"));
    }

    @Test
    void setLastName() {
        Persone pers= new Persone("Marius","Guga");
        pers.setLastName("Malecu");
        assertTrue(pers.getLastName().equals("Malecu"));
    }

    @Test
    void testEquals() {
        Persone pers1= new Persone("Marius","Guga");
        Persone pers2= new Persone("Marius","Guga");
        Persone pers3= new Persone("Marius","Buga");
        assertTrue(pers1.equals(pers2));
        assertFalse(pers1.equals(pers3));
    }

    @Test
    void testToString() {
        Persone pers= new Persone("Marius","Guga");
        assertTrue(pers.toString().equals("Persone{"+
                "firstName='" + "Marius" + '\'' +
                ", lastName='" + "Guga" + '\'' +
                '}'));
    }
}