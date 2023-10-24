package com.example.tests.repository.db;

import com.example.Domain.Persone;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Repository.Db.PersoneDbRepo;
import com.example.Repository.Db.RequestsDbRepo;
import com.example.Repository.Db.UserDbRepo;
import com.example.Repository.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.tests.Connections;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RequestsDbRepoTest {

    @BeforeEach
    void setUp() {
        tearDown();
        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.save(new Persone(1L,"wewe","weew"));
        repoP.save(new Persone(2L,"weew","erui"));
        repoP.closeConnection();
        UserRepo repo=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.save(new User(1L,"a",new Persone(1L,"wewe","weew")));
        repo.save(new User(2L,"biscuit",new Persone(1L,"wewe","weew")));
        repo.save(new User(3L,"macaron",new Persone(1L,"wewe","weew")));
        repo.closeConnection();
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoQ.save(new Relationship(1L,"a","biscuit",LocalDate.of(2021,12,11),"pending"));
        repoQ.save(new Relationship(2L,"a","macaron",LocalDate.of(2021,12,11),"pending"));
        repoQ.closeConnection();
    }

    @AfterEach
    void tearDown() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoQ.restoreToDefault();
        repoQ.closeConnection();
        UserDbRepo repo=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.restoreToDefault();
        repo.closeConnection();
        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.restoreToDefault();
        repoP.closeConnection();

    }

    @Test
    void save() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        try {
            Relationship rel = new Relationship(3L, "macaron", "biscuit", LocalDate.now(), "pending");
            repoQ.save(rel);
            assertTrue(true);
        }catch(Exception e){
            assertTrue(false);
        }
        repoQ.closeConnection();
    }

    @Test
    void saveExistingId() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        try {
            Relationship rel = new Relationship(2L, "macaron", "biscuit", LocalDate.now(), "pending");
            repoQ.save(rel);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        repoQ.closeConnection();
    }


    @Test
    void update() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        try {
            Relationship rel = new Relationship(1L,"a", "biscuit", LocalDate.now(), "accept");
            repoQ.update(1L,rel);
            assertEquals(repoQ.get(1L),rel);
            assertTrue(true);
        }catch(Exception e){
            System.out.println(e.getMessage());
            assertTrue(false);
        }
        repoQ.closeConnection();
    }

    @Test
    void updateInexistingId() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        try {
            Relationship rel = new Relationship(1L,"a", "biscuit", LocalDate.now(), "accept");
            repoQ.update(3L,rel);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        repoQ.closeConnection();
    }

    @Test
    void getAll() {
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        assertEquals(repoQ.getAll().size(),2);
        repoQ.closeConnection();
    }

    @Test
    void get(){
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        assertEquals(repoQ.get(1L),new Relationship(1L,"a","biscuit",LocalDate.of(2021,12,11),"pending"));
        repoQ.closeConnection();
    }

    @Test
    void getInexisted(){
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        assertEquals(repoQ.get(3L),null);
        repoQ.closeConnection();
    }

    @Test
    void delete(){
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        assertTrue(repoQ.delete(1L));
        assertEquals(repoQ.get(3L),null);
        repoQ.closeConnection();
    }

    @Test
    void deleteNull(){
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        try {
            assertTrue(repoQ.delete(null));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        repoQ.closeConnection();
    }

    @Test
    void deleteInexisting(){
        RequestsDbRepo repoQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        try {
            assertTrue(repoQ.delete(null));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        repoQ.closeConnection();
    }
}