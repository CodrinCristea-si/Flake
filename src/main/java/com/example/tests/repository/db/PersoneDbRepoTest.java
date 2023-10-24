package com.example.tests.repository.db;

import com.example.Domain.Persone;
import com.example.Repository.Db.PersoneDbRepo;
import com.example.Utils.Exceptions.EntityRepoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.tests.Connections;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersoneDbRepoTest {
    @BeforeEach
    void setUp(){
        PersoneDbRepo repo=new PersoneDbRepo(Connections.URL, Connections.Username,Connections.Password);
        repo.save(new Persone(1L,"wewe","weew"));
        repo.save(new Persone(2L,"wewe","weew"));
        repo.save(new Persone(3L,"mama","mea"));
        repo.closeConnection();
    }

    @AfterEach
    void tearDown(){
        PersoneDbRepo repo=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.restoreToDefault();
        repo.closeConnection();
    }

    @Test
    void save() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try {
            assertTrue(
                    repo.save(new Persone(4L, "wwne", "reje")));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
        repo.closeConnection();
    }

    @Test
    void saveWithNoID() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try {
            assertTrue(
                    repo.save(new Persone("jerk", "erjk")));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
        repo.closeConnection();
    }

    @Test
    void saveExistentId(){
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(repo.save(new Persone(1L,"jkwe","wejk")));
        }catch(EntityRepoException e){
            assertTrue(true);
        }
        assertTrue(repo.getSize()==3);
        repo.closeConnection();
    }

    @Test
    void get() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        Persone pers= repo.get(1L);
        assertEquals(new Persone(1L,"wewe","weew"),pers);
        repo.closeConnection();
    }

    @Test
    void getNull() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        Persone pers= repo.get(10L);
        assertEquals(pers,null);
        repo.closeConnection();
    }

    @Test
    void update() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.update(1L,new Persone(5L,"weew","erui")));
            assertNull(repo.get(1L));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),3);
        repo.closeConnection();
    }

    @Test
    void updateInexistent() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(repo.update(7L,new Persone(2L,"weew","erui")));
            assertTrue(false);
        }
        catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
        repo.closeConnection();
    }

    @Test
    void delete() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.delete(1L));
            assertNull(repo.get(1L));
        }catch(Exception e){
            assertTrue(false);
        }
        repo.closeConnection();
    }

    @Test
    void deleteInexistent() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.delete(5L));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        repo.closeConnection();
    }

    @Test
    void getAll() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        List<Persone> list=repo.getAll();
        List<Persone> comparableList= Arrays.asList(new Persone(1L,"wewe","weew"),
                new Persone(2L,"wewe","weew"),
                new Persone(3L,"mama","mea"));
        assertEquals(list,comparableList);
        repo.closeConnection();
    }


    @Test
    void getByOther() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertEquals(repo.getByOther("mama","mea"),new Persone(3L,"mama","mea"));
        }catch(Exception e){
            assertTrue(false);
        }
        repo.closeConnection();
    }

    @Test
    void getByOtherInexistent() {
        PersoneDbRepo repo = new PersoneDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertEquals(repo.getByOther("hjer","hjwek"),null);
        }catch(Exception e){
            assertTrue(false);
        }
        repo.closeConnection();
    }

}