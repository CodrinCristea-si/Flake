package com.example.tests.domain;

import com.example.Domain.Relationship;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


class RelationshipTest {

    @Test
    void getId() {
        Relationship rel = new Relationship(1L,"acadea","bucales");
        assertEquals(rel.getId(),1L);
    }

    @Test
    void setId() {
        Relationship rel = new Relationship("acadea","bucales");
        assertEquals(rel.getId(),null);
        rel.setId(1L);
        assertEquals(rel.getId(),1L);
    }

    @Test
    void getFirstUserName() {
        Relationship rel = new Relationship("acadea","bucales");
        assertEquals(rel.getFirstUserName(),"acadea");
    }

    @Test
    void setFirstUserName() {
        Relationship rel = new Relationship("acadea","bucales");
        rel.setFirstUserName("animal");
        assertEquals(rel.getFirstUserName(),"animal");
    }

    @Test
    void getSecondUserName() {
        Relationship rel = new Relationship("acadea","bucales");
        assertEquals(rel.getSecondUserName(),"bucales");
    }

    @Test
    void setSecondUserName() {
        Relationship rel = new Relationship("acadea","bucales");
        rel.setSecondUserName("maricica");
        assertEquals(rel.getSecondUserName(),"maricica");
    }

    @Test
    void getTime() {

        Relationship rel = new Relationship(1L,"acadea","bucales",
                LocalDate.of(2018, 02, 27));

        assertEquals(rel.getDtf().toString(), "2018-02-27");
    }
    @Test
    void setTime() {
        Relationship rel = new Relationship(1L,"acadea","bucales",
                LocalDate.of(2018, 02, 27));
        rel.setDtf( LocalDate.of(2019, 02, 10));
        assertEquals(rel.getDtf().toString(), "2019-02-10");
    }
    @Test

    void testEquals() {
        Relationship rel = new Relationship("acadea","bucales");
        Relationship rel1 = new Relationship("acadea","bucales");
        assertEquals(rel,rel1);

        rel1.setFirstUserName("maricica");
        assertFalse(rel.equals(rel1));

        rel.setId(1L);
        rel1.setId(1L);
        assertEquals(rel,rel1);

        rel1.setId(2L);
        assertFalse(rel.equals(rel1));

    }

    @Test
    void testToString() {
        Relationship rel = new Relationship("acadea","bucales");
        assertEquals(rel.toString(),"Relationship{" + " between "
                    +'\''+ "acadea" + '\'' + " and " +
                    '\'' + "bucales" + '\'' +
                    '}');

        rel.setId(1L);

        rel.setDtf(LocalDate.of(2020,12,12));

        assertEquals(rel.toString(),"Relationship{" +
                "id= "+ "1" + " between " +
                '\''+ "acadea" + '\'' + " and " +
                '\'' + "bucales" + '\'' +
                " started at  2020-12-12"+
                '}');
    }

}