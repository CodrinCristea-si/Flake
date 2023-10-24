package com.example.tests.controller.newer;

import com.example.Controller.NewController.*;
import com.example.Domain.Message;
import com.example.Domain.Persone;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Repository.Db.*;
import com.example.Repository.RelationshipRepo;
import com.example.Repository.UserRepo;
import com.example.Utils.Exceptions.UserRepoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.tests.Connections;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainControllerTest {

    @BeforeEach
    void setUp() {
        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.save(new Persone(1L,"wewe","weew"));
        repoP.save(new Persone(2L,"weew","erui"));
        repoP.save(new Persone(3L,"weew","erui1"));
        repoP.save(new Persone(4L,"weew","erui2"));
        repoP.save(new Persone(5L,"weew","erui3"));
        repoP.save(new Persone(6L,"weew","erui4"));
        repoP.save(new Persone(7L,"weew","erui5"));
        repoP.save(new Persone(8L,"weew","erui6"));
        repoP.save(new Persone(9L,"weew","erui7"));
        repoP.save(new Persone(10L,"weew","erui8"));
        repoP.closeConnection();

        UserRepo repoU=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoU.save(new User(1L,"mama",new Persone(1L,"wewe","weew")));
        repoU.save(new User(2L,"mea",new Persone(2L,"wewe","erui")));
        repoU.save(new User(3L,"biscuit",new Persone(3L,"wewe","erui1")));
        repoU.save(new User(4L,"macaron",new Persone(4L,"wewe","erui2")));
        repoU.save(new User(5L,"belea",new Persone(5L,"wewe","erui3")));
        repoU.save(new User(6L,"noua",new Persone(6L,"wewe","erui4")));
        repoU.save(new User(7L,"blah",new Persone(7L,"wewe","erui5")));
        repoU.save(new User(8L,"meh",new Persone(8L,"wewe","erui6")));
        repoU.save(new User(9L,"vespuci",new Persone(9L,"wewe","erui7")));
        repoU.save(new User(10L,"acadea",new Persone(10L,"wewe","erui8")));
        repoU.closeConnection();

        RelationshipDbRepo repo=new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.save(new Relationship(1L,"mama","mea",LocalDate.of(
                2021,11,21
        )));
        repo.save(new Relationship(2L,"biscuit","mama",LocalDate.of(
                2021,11,21)));
        repo.save(new Relationship(3L,"macaron","mea",LocalDate.of(
                2021,11,21)));
        repo.closeConnection();

        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoRQ.save(new Relationship(1L,"mama","belea",LocalDate.of(2021,11,22),"pending"));
        repoRQ.save(new Relationship(2L,"mama","noua",LocalDate.of(2021,11,22),"pending"));
        repoRQ.save(new Relationship(3L,"mama","mea",LocalDate.of(2021,11,21),"accept"));
        repoRQ.save(new Relationship(4L,"biscuit","mama",LocalDate.of(2021,11,21),"accept"));
        repoRQ.save(new Relationship(5L,"macaron","mea",LocalDate.of(2021,11,21),"accept"));
        repoRQ.closeConnection();

        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoM.save(new Message(1L,new User(1L,"mama",new Persone(1L,"wewe","weew")),"hello", Arrays.asList(new User(2L,"mea",new Persone(2L,"wewe","erui")),new User(3L,"biscuit",new Persone(3L,"wewe","erui1"))), LocalDateTime.of(2019,11,2,12,53)));
        repoM.save(new Message(2L,new User(1L,"mama",new Persone(1L,"wewe","weew")),"asl pls", Arrays.asList(new User(2L,"mea",new Persone(2L,"wewe","erui"))), LocalDateTime.of(2019,11,2,12,53)));
        repoM.save(new Message(3L,new User(2L,"mea",new Persone(2L,"wewe","erui")),"20, woman, Cluj", Arrays.asList(new User(1L,"mama",new Persone(1L,"wewe","weew"))), LocalDateTime.of(2019,11,2,12,53)));
        repoM.closeConnection();
    }

    @AfterEach
    void tearDown() {
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoRQ.restoreToDefault();
        repoRQ.closeConnection();

        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoM.restoreToDefault();
        repoM.closeConnection();

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
    void addUserUnchangedPersone() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertTrue(cont.addUser(new User("malala",new Persone(1L,"wewe","weew"))));
        assertEquals(contU.getSize(),11);
        assertEquals(contP.getSize(),10);
        cont.closeConnections();
    }

    @Test
    void addUserChangedPersone() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertTrue(cont.addUser(new User("malala",new Persone(5L,"abraham","link"))));
        assertEquals(contU.getSize(),11);
        assertEquals(repoP.getSize(),10);
        cont.closeConnections();
    }

    @Test
    void addExistingUser() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);



        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertTrue(cont.addUser(new User("acadea", new Persone(5L, "abraham", "link"))));
            assertTrue(false);
        }catch(UserRepoException e){
            assertTrue(true);

        }finally{
            assertEquals(contU.getSize(),10);
            assertEquals(repoP.getSize(),10);
        }
        cont.closeConnections();
    }

    @Test
    void addRelationship() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);


        assertTrue(cont.addRelationship(new Relationship(6L,"blah","meh",
                LocalDate.of(2020,11,21))));


        assertEquals(contR.getSize(),4);
        assertEquals(contU.getSize(),10);
        assertEquals(contP.getSize(),10);
        cont.closeConnections();
    }

    @Test
    void addRelationshipInexistingFirstUser() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);



        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertTrue(cont.addRelationship(new Relationship("b", "meh")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }finally {
            assertEquals(contR.getSize(), 3);
        }
        cont.closeConnections();
    }

    @Test
    void addRelationshipInexistingSecondUser() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);



        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertTrue(cont.addRelationship(new Relationship("blah", "m")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }finally {
            assertEquals(contR.getSize(), 3);
        }
        cont.closeConnections();
    }

    @Test
    void addExistingRelationship() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);



        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertTrue(cont.addRelationship(new Relationship("mama", "mea")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }finally {
            assertEquals(contR.getSize(), 3);
        }
        cont.closeConnections();
    }

    @Test
    void addExistingInvertRelationship() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertTrue(cont.addRelationship(new Relationship("mea", "mama")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }finally {
            assertEquals(contR.getSize(), 3);
        }
        cont.closeConnections();
    }

    @Test
    void removeByUserId() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);


        cont.addRelationship(new Relationship(4L,"mama","noua",
                LocalDate.of(2020,11,21)));
        cont.addRelationship(new Relationship(5L,"mama","belea",
                LocalDate.of(2020,11,21)));



        assertTrue(cont.removeUserById(1L));
        assertEquals(contR.getSize(),1);
        assertEquals(contU.getSize(),9);
        assertEquals(contP.getSize(),10);
        cont.closeConnections();
    }

    @Test
    void removeByUserIdRemovePersone() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);

        int lenght=contU.getSize();
        for(Long i=1L;i<=lenght;i++){
            cont.removeUserById(i);
        }

        assertEquals(contR.getSize(),0);
        assertEquals(contU.getSize(),0);
        assertEquals(contP.getSize(),9);
        cont.closeConnections();
    }

    @Test
    void removeByInexistedUserId() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            assertTrue(cont.removeUserById(15L));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 3);
            assertEquals(contU.getSize(), 10);
            assertEquals(contP.getSize(), 10);
            cont.closeConnections();
        }
    }

    @Test
    void removeByRelationshipId() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);


        cont.addRelationship(new Relationship(5L,"mama","noua",
                LocalDate.of(2020,11,21)));
        cont.addRelationship(new Relationship(6L,"mama","belea",
                LocalDate.of(2020,11,21)));



        assertTrue(cont.removeRelationshipById(5L));
        assertEquals(contR.getSize(),4);
        assertEquals(contU.getSize(),10);
        assertEquals(contP.getSize(),10);
        cont.closeConnections();
    }

    @Test
    void removeByInexistedRelationshipdId() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);



        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            assertTrue(cont.removeRelationshipById(15L));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 3);
            assertEquals(contU.getSize(), 10);
            assertEquals(contP.getSize(), 10);
            cont.closeConnections();
        }

    }

    @Test
    void removeUserByUsername() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);


        cont.addRelationship(new Relationship(12L,"mama","noua",
        LocalDate.of(2020,11,21)));
        cont.addRelationship(new Relationship(22L,"mama","belea",
                LocalDate.of(2020,11,21)));



        assertTrue(cont.removeUserByUsername("mama"));
        assertEquals(contR.getSize(),1);
        assertEquals(contU.getSize(),9);
        assertEquals(contP.getSize(),10);
        cont.closeConnections();
    }

    @Test
    void removeUserByUsernameRemovePersone() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);

        cont.removeUserByUsername("mama");cont.removeUserByUsername("mea");
        cont.removeUserByUsername("biscuit");cont.removeUserByUsername("macaron");
        cont.removeUserByUsername("belea");cont.removeUserByUsername("noua");
        cont.removeUserByUsername("meh");cont.removeUserByUsername("blah");
        cont.removeUserByUsername("vespuci");cont.removeUserByUsername("acadea");

        assertEquals(contR.getSize(),0);
        assertEquals(contU.getSize(),0);
        assertEquals(contP.getSize(),9);
        cont.closeConnections();
    }

    @Test
    void removeInexistingUserByUsername() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);



        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            assertTrue(cont.removeUserByUsername("jweih"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 3);
            assertEquals(contU.getSize(), 10);
            assertEquals(contP.getSize(), 10);
            cont.closeConnections();
        }
    }


    @Test
    void removeX2User() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);
        try{
            assertTrue(cont.removeUserById(1L));
            assertTrue(cont.removeUserByUsername("mama"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 1);
            assertEquals(contU.getSize(), 9);
            assertEquals(contP.getSize(), 10);
            cont.closeConnections();
        }
    }

    @Test
    void removeRelationshipByUsernames() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        cont.addRelationship(new Relationship(5L,"mama","noua",
                LocalDate.of(2020,11,21)));
        cont.addRelationship(new Relationship(6L,"mama","belea",
                LocalDate.of(2020,11,21)));



        assertTrue(cont.removeRelationshipByUsernames("belea","mama"));
        assertEquals(contR.getSize(),4);
        assertEquals(contU.getSize(),10);
        assertEquals(contP.getSize(),10);
        cont.closeConnections();
    }

    @Test
    void removeInexistedRelationshipByUsernamesd() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            assertTrue(cont.removeRelationshipByUsernames("capri","maluma"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 3);
            assertEquals(contU.getSize(), 10);
            assertEquals(contP.getSize(), 10);
            cont.closeConnections();
        }
    }

    @Test
    void removeX2Relationship() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            cont.removeRelationshipById(1L);
            assertTrue(cont.removeRelationshipByUsernames("mama","mea"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        finally {
            assertEquals(contR.getSize(), 2);
            assertEquals(contU.getSize(), 10);
            assertEquals(contP.getSize(), 10);
            cont.closeConnections();
        }
    }

    @Test
    void getUserById() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getUserById(1L),new User(1L,"mama",new Persone(1L,"wewe","weew")));
        cont.closeConnections();
    }

    @Test
    void getInexistedUserById() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);



        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertEquals(cont.getUserById(15L), null);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        cont.closeConnections();
    }

    @Test
    void getRelationshipById() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getRelationshipById(1L),new Relationship(1L,"mama","mea"));
        cont.closeConnections();
    }

    @Test
    void getInexistedRelationshipById() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertEquals(cont.getRelationshipById(15L), null);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        cont.closeConnections();
    }

    @Test
    void getUserByOther() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        assertEquals(cont.getUserByOther("mama"),new User(1L,"mama",new Persone(1L,"wewe","weew")));

    }

    @Test
    void getInexistedUserByOther() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertEquals(cont.getUserByOther("hjew"), null);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        cont.closeConnections();
    }

    @Test
    void getRelationshipByOther() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getRelationshipByOther("mama","mea"),new Relationship(1L,"mama","mea"));
        cont.closeConnections();
    }

    @Test
    void getInexistedRelationshipByOther() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        try {
            assertEquals(cont.getRelationshipByOther("wewwe","wehhjew"), null);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        cont.closeConnections();
    }

    @Test
    void getAllUsers() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        List<User> list=cont.getAllUsers();
        assertEquals(list.size(),10);

        User user=new User(1L,"mama",new Persone("wewe","weew"));
        user.addFriend("mea");
        user.addFriend("biscuit");
        assertEquals(list.get(5),user);
        cont.closeConnections();
    }

    @Test
    void getAllRelationships() {
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        List<Relationship> list=cont.getAllRelationships();
        assertEquals(list.size(),3);

        Relationship rel= new Relationship(1L,"mama","mea",
                LocalDate.of(2021,11,21));


        assertEquals(list.get(0),rel);
        cont.closeConnections();
    }

    @Test
    void getUserSize() {

        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getUserSize(),10);
        cont.closeConnections();
    }

    @Test
    void getRelationshipSize() {

        UserRepo repoU= new UserDbRepo(Connections.URL, Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getRelationshipSize(),3);
        cont.closeConnections();
    }

    @Test
    void getFriendsByUsername(){
        UserRepo repoU= new UserDbRepo(Connections.URL, Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        Map<Persone,LocalDate> map1=new HashMap<>();
        map1.put(new Persone("weew","erui1"),LocalDate.parse("2021-11-21"));
        map1.put(new Persone("weew", "erui"),LocalDate.parse("2021-11-21"));
        assertEquals(cont.getFriendsByUsername("mama"), map1);
        cont.closeConnections();
    }

    @Test
    void getFriendsByUsernameAndMonth(){
        UserRepo repoU= new UserDbRepo(Connections.URL, Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        Map<Persone,LocalDate> map1=new HashMap<>();
        map1.put(new Persone("weew","erui1"),LocalDate.parse("2021-11-21"));
        map1.put(new Persone("weew", "erui"),LocalDate.parse("2021-11-21"));
        assertEquals(cont.getFriendsByUsernameAndMonth("mama",11),map1 );
        cont.closeConnections();
    }

    @Test
    void getUserByUsername(){
        UserRepo repoU= new UserDbRepo(Connections.URL, Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);

        assertEquals(cont.getUserByUsername("mama"),new User(1L,"mama",new Persone(1L,"wewe","weew")));
        cont.closeConnections();
    }

    @Test
    void getUserByInexistingUsername(){
        UserRepo repoU= new UserDbRepo(Connections.URL, Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        MainController cont= new MainController(contU,contR,contP);
        try{
            cont.getUserByUsername("ewhj");
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        cont.closeConnections();
    }

    @Test
    void loadConversation(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM);

        assertEquals(cont.loadConversation("mama","mea").size(),3);
        assertEquals(cont.loadConversation("biscuit","mama").size(),1);
        cont.closeConnections();
    }

    @Test
    void sendMessage(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM);

        assertTrue(cont.sendMessage(new Message(4L,new User(1L,"mama",new Persone(1L,"wewe","weew")),"how are u", Arrays.asList(new User(2L,"mea",new Persone(2L,"wewe","erui"))), LocalDateTime.of(2019,11,2,12,53))));
        cont.closeConnections();
    }

    @Test
    void sendMessageNotRelation(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM);
        try {
            assertTrue(cont.sendMessage(new Message(4L, new User(1L, "mama", new Persone(1L, "wewe", "weew")), "how are u", Arrays.asList(new User(2L, "belea", new Persone(2L, "wewe", "erui"))), LocalDateTime.of(2019, 11, 2, 12, 53))));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        cont.closeConnections();
    }

    @Test
    void sendMessageToAll(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM);

        assertTrue(cont.sendMessageToAll(new Message(4L,new User(1L,"mama",new Persone(1L,"wewe","weew")),"how are u guys", Arrays.asList(new User(2L,"mea",new Persone(2L,"wewe","erui")),new User(3L,"biscuit",new Persone(3L,"wewe","erui1"))), LocalDateTime.of(2019,11,2,12,53))));
        cont.closeConnections();
    }

    @Test
    void sendMessageToAllError(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM);

        try {
            assertTrue(cont.sendMessageToAll(new Message(4L,new User(1L,"mama",new Persone(1L,"wewe","weew")),"how are u guys", Arrays.asList(new User(2L,"mea",new Persone(2L,"wewe","erui")),new User(3L,"belea",new Persone(3L,"wewe","erui1"))), LocalDateTime.of(2019,11,2,12,53))));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        cont.closeConnections();
    }

    @Test
    void getAllRequests(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);

        assertEquals(cont.getAllRequests().size(),5);
        cont.closeConnections();
    }

    @Test
    void getFriendsRequests(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);

        assertEquals(cont.getFriendshipsRequests("belea"),Arrays.asList("mama"));
        assertTrue(cont.getFriendshipsRequests("mama").isEmpty());
        cont.closeConnections();
    }

    @Test
    void getUpdateStatusAccept(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);

        cont.UpdateStatusRequest("accept","mama","belea");
        assertEquals(contR.getSize(),4);
        cont.closeConnections();
    }

    @Test
    void getUpdateStatusReject(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);

        cont.UpdateStatusRequest("reject","mama","belea");
        assertEquals(contR.getSize(),3);
        cont.closeConnections();
    }

    @Test
    void AddRequest(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);

        assertTrue(cont.AddRequest(new Relationship("belea","noua",LocalDate.of(2021,12,11),"pending")));
        cont.closeConnections();
    }

    @Test
    void AddSameRequest(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);
        try {
            assertTrue(cont.AddRequest(new Relationship(1L, "mama", "belea", LocalDate.of(2021, 11, 22), "pending")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        cont.closeConnections();
    }

    @Test
    void AddAcceptedRequest(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);
        try {
            assertTrue(cont.AddRequest(new Relationship(1L, "mama", "mea", LocalDate.of(2021, 11, 22), "pending")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        cont.closeConnections();
    }

    @Test
    void AddInvertRequest(){
        UserRepo repoU= new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(Connections.URL,Connections.Username,Connections.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(Connections.URL,Connections.Username,Connections.Password);
        MessageDbRepo repoM=new MessageDbRepo(Connections.URL,Connections.Username,Connections.Password);


        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);

        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ);
        try {
            assertTrue(cont.AddRequest(new Relationship(1L, "belea","mama", LocalDate.of(2021, 11, 22), "pending")));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        cont.closeConnections();
    }
}