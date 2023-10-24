package com.example.tests.controller.validator;

import com.example.Controller.Validator.PersoneValidator;
import com.example.Controller.Validator.Validator;
import com.example.Domain.Persone;
import com.example.Utils.Exceptions.PersoneException;
import org.junit.jupiter.api.Test;

import com.example.Utils.Exceptions.*;
import com.example.Utils.Exceptions.Exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersoneValidatorTest {

    @Test
    void validate() {
        Persone pers=new Persone("iura","gara-bincea");
        Validator<Persone> vali= new PersoneValidator();
        assertTrue(vali.validate(pers));
    }

    @Test
    void checkNameForEmpty() {
        Persone pers=new Persone("iura","");
        Validator<Persone> vali= new PersoneValidator();
        try {
            vali.validate(pers);
        }
        catch(PersoneException e){
            assertTrue(e.getCode()==1);
            assertEquals(e.getDescription(),"The first name and the last name shouldn't be empty\n");
        }

        Persone pers1=new Persone("","gara-bincea");
        try {
            vali.validate(pers1);
        }
        catch(PersoneException e){
            assertTrue(e.getCode()==1);
            assertEquals(e.getDescription(),"The first name and the last name shouldn't be empty\n");
        }

        Persone pers2=new Persone("","");
        try {
            vali.validate(pers2);
        }
        catch(PersoneException e){
            assertTrue(e.getCode()==1);
            assertEquals(e.getDescription(),"The first name and the last name shouldn't be empty\n");
        }
    }

    @Test
    void checkNameForInvalidChar() {
        Persone pers=new Persone("iura1","gara-bincea");
        Validator<Persone> vali= new PersoneValidator();
        try {
            vali.validate(pers);
        }
        catch(PersoneException e){
            assertTrue(e.getCode()==1);
            assertEquals(e.getDescription(),"The first name and the last name should contain english characters");
        }

        Persone pers1=new Persone("iura","gara-bincea$");
        try {
            vali.validate(pers1);
        }
        catch(PersoneException e){
            assertTrue(e.getCode()==1);
            assertEquals(e.getDescription(),"The first name and the last name should contain english characters");
        }

        Persone pers2=new Persone("323","gar9^a$");
        try {
            vali.validate(pers1);
        }
        catch(PersoneException e){
            assertTrue(e.getCode()==1);
            assertEquals(e.getDescription(),"The first name and the last name should contain english characters");
        }
    }
}