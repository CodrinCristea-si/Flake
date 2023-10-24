package com.example.tests.repository.db;

import com.example.Domain.Persone;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Repository.Db.PersoneDbRepo;
import com.example.Repository.Db.RelationshipDbRepo;
import com.example.Repository.Db.UserDbRepo;
import com.example.Repository.UserRepo;
import com.example.Utils.Exceptions.EntityRepoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.tests.Connections;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipDbRepoTest {
    @BeforeEach
    void setUp(){

        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.save(new Persone(1L,"wewe","weew"));
        repoP.save(new Persone(2L,"weew","erui"));
        repoP.closeConnection();
        UserRepo repoU=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoU.closeConnection();
        repoU.save(new User(1L,"mama",new Persone(1L,"wewe","weew")));
        repoU.save(new User(2L,"mea",new Persone(1L,"wewe","weew")));
        repoU.save(new User(3L,"biscuit",new Persone(1L,"wewe","weew")));
        repoU.save(new User(4L,"macaron",new Persone(1L,"wewe","weew")));
        repoU.save(new User(5L,"belea",new Persone(1L,"wewe","weew")));
        repoU.save(new User(6L,"noua",new Persone(1L,"wewe","weew")));
        repoU.save(new User(7L,"blah",new Persone(1L,"wewe","weew")));
        repoU.save(new User(8L,"meh",new Persone(1L,"wewe","weew")));
        repoU.save(new User(9L,"vespuci",new Persone(1L,"wewe","weew")));
        repoU.save(new User(10L,"acadea",new Persone(1L,"wewe","weew")));

        RelationshipDbRepo repo=new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.save(new Relationship(1L,"mama","mea",
                LocalDate.of(2020,10,10)));
        repo.save(new Relationship(2L,"biscuit","mama",
                LocalDate.of(2020,10,10)));
        repo.save(new Relationship(3L,"macaron","mea",
                LocalDate.of(2020,10,10)));
        repo.closeConnection();

    }

    @AfterEach
    void tearDown(){
        RelationshipDbRepo repo=new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.restoreToDefault();
        repo.closeConnection();

        UserDbRepo repoU=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoU.restoreToDefault();
        repoU.closeConnection();
        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.restoreToDefault();
        repoP.closeConnection();
    }

    @Test
    void save() {

        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try {
            assertTrue(
                    repo.save(new Relationship(4L, "belea", "noua",
                            LocalDate.of(2021, 11, 21))));
            } catch (EntityRepoException e) {
                System.out.println(e.getDescription());
                assertTrue(false);
            }
            assertEquals(repo.getSize(), 4);
        repo.closeConnection();
    }

    @Test
    void saveWithNoID() {
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try {
            assertTrue(
                    repo.save(new Relationship("blah", "meh")));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
        repo.closeConnection();
    }

    @Test
    void saveExistentUsernames(){
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.save(new Relationship(5L,"mama","mea",
                            LocalDate.of(2020,10,10))));
        }catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
        repo.closeConnection();
    }

    @Test
    void get() {
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        Relationship rel= repo.get(1L);
        assertEquals(new Relationship(1L,"mama","mea"),rel);
        repo.closeConnection();
    }

    @Test
    void getNull() {
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        Relationship rel= repo.get(10L);
        assertNull(rel);
        repo.closeConnection();
    }

    @Test
    void update() {
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.update(1L,new Relationship(5L,"acadea","vespuci")));
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
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(repo.update(7L,new Relationship(5L,"acadea","erui")));
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
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.delete(1L));
        }catch(Exception e){
            assertTrue(false);
        }
        repo.closeConnection();
    }

    @Test
    void deleteInexistent() {
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
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
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        List<Relationship> list=repo.getAll();
        List<Relationship> comparableList= Arrays.asList(new Relationship(1L,"mama","mea"),
                new Relationship(2L,"biscuit","mama"),
                new Relationship(3L,"macaron","mea"));
        assertEquals(list,comparableList);
        repo.closeConnection();
    }


    @Test
    void getByOther() {
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertEquals(repo.getByOther("biscuit","mama"),new Relationship(2L,"biscuit","mama"));
        }catch(Exception e){
            assertTrue(false);
        }
        repo.closeConnection();
    }

    @Test
    void getByOtherInexistent() {
        RelationshipDbRepo repo = new RelationshipDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertEquals(repo.getByOther("hjer","mea"),null);
        }catch(Exception e){
            assertTrue(false);
        }
        repo.closeConnection();
    }
}