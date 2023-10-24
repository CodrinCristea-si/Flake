package com.example.tests.controller.old;

import com.example.Controller.OldController.UserController;
import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Repository.Memory.UserMemoryRepo;
import org.junit.jupiter.api.Test;

import com.example.Utils.Exceptions.*;
import com.example.Utils.Exceptions.Exception;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest {


    @Test
    void add() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        try {
            cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    void addException() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        try {
            cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }

        try {
            cont.add(new User(2L, "maimuta", new Persone("Marieta", "Gazela")));
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void removeById() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        try {
            cont.removeById(1L);
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    void removeByIdException() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        try {
            cont.removeById(2L);
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void removeByOthers() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        try {
            cont.removeByOthers("maimuta");
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    void removeByOthersException() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        try {
            cont.removeByOthers("mahala");
            assertTrue(false);
        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getById() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        assertTrue(cont.getById(1L).getUsername().equals("maimuta"));
        assertTrue(cont.getById(3L)==null);
    }

    @Test
    void getByOther() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        assertTrue(cont.getByOther("elenamea").getPers().getFirstName().equals("Marieta"));
        assertTrue(cont.getByOther("heeihriur")==null);
    }

    @Test
    void getAll() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        cont.add(new User(3L, "academicul", new Persone("varza", "murata")));
        List<User> list=cont.getAll();
        assertEquals(list.size(),3);

        assertEquals(list.get(0).getUsername(),"maimuta");
        assertEquals(list.get(1).getUsername(),"elenamea");
        assertEquals(list.get(2).getUsername(),"academicul");

    }

    @Test
    void getSize() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));

        assertEquals(cont.getSize(),2);
        cont.add(new User(3L, "academicul", new Persone("varza", "murata")));
        assertEquals(cont.getSize(),3);
    }

    @Test
    void addFriend() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        cont.addFriend(cont.getById(1L),"elenamea");
        assertEquals(cont.getById(1L).getFriendsList().size(),1);
        assertEquals(cont.getById(1L).getFriendsList().get(0),"elenamea");
        assertEquals(cont.getById(2L).getFriendsList().size(),0);
    }

    @Test
    void removeFriend() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        cont.addFriend(cont.getById(1L),"elenamea");
        cont.removeFriend(cont.getById(1L),"elenamea");
        assertEquals(cont.getById(1L).getFriendsList().size(),0);
    }

    @Test
    void removeUserFromAllFriends() {
        UserMemoryRepo repo = new UserMemoryRepo();
        UserController cont = new UserController(repo);
        cont.add(new User(1L, "maimuta", new Persone("Marieta", "Gazela")));
        cont.add(new User(2L, "elenamea", new Persone("Marieta", "Gazela")));
        cont.add(new User(3L, "academicul", new Persone("varza", "murata")));
        cont.addFriend(cont.getById(1L),"elenamea");
        cont.addFriend(cont.getById(3L),"elenamea");
        cont.removeUserFromAllFriends("elenamea");
        assertEquals(cont.getById(1L).getFriendsList().size(),0);
        assertEquals(cont.getById(3L).getFriendsList().size(),0);
    }
}