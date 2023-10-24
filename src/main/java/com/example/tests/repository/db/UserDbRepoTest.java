package com.example.tests.repository.db;

import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Repository.Db.PersoneDbRepo;
import com.example.Repository.Db.UserDbRepo;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.Pageble;
import com.example.Repository.UserRepo;
import com.example.Utils.Exceptions.EntityRepoException;
import com.example.Utils.Exceptions.UserRepoException;
import com.example.tests.Connections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDbRepoTest {
    @BeforeEach
    void setUp(){
        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.save(new Persone(1L,"wewe","weew"));
        repoP.save(new Persone(2L,"weew","erui"));
        repoP.closeConnection();

        UserDbRepo repo=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.save(new User(1L,"a",new Persone(1L,"wewe","weew")));
        repo.save(new User(2L,"biscuit",new Persone(1L,"wewe","weew")));
        repo.save(new User(3L,"macaron",new Persone(1L,"wewe","weew")));
        repo.closeConnection();
    }

    @AfterEach
    void tearDown(){


        UserDbRepo repo=new UserDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repo.restoreToDefault();
        repo.closeConnection();

        PersoneDbRepo repoP=new PersoneDbRepo(Connections.URL,Connections.Username,Connections.Password);
        repoP.restoreToDefault();
        repoP.closeConnection();
    }

    @Test
    void save() {

        UserDbRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try {
            assertTrue(
                    repo.save(new User(4L, "belea", new Persone(1L, "wewe", "weew"))));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
        repo.closeConnection();
    }

    @Test
    void saveWithNoID() {

        UserDbRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        try {
            assertTrue(
                    repo.save(new User("blah", new Persone(1L, "wewe", "weew"))));
        } catch (EntityRepoException e) {
            System.out.println(e.getDescription());
            assertTrue(false);
        }
        assertEquals(repo.getSize(),4);
        repo.closeConnection();
    }

    @Test
    void saveExistentId(){

        UserDbRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.save(new User(1L,"acadeaua",new Persone(1L,"wewe","weew"))));
        }catch(UserRepoException e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
        repo.closeConnection();
    }

    @Test
    void saveExistentUsername(){

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.save(new User(5L,"a",new Persone(1L,"wewe","weew"))));
        }catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
        repo.closeConnection();
    }

    @Test
    void saveExistentIdAndUsername(){

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(
                    repo.save(new User(1L,"a",new Persone(2L,"wewe","weew"))));
        }catch(Exception e){
            assertTrue(true);
        }
        assertEquals(repo.getSize(),3);
        repo.closeConnection();
    }

    @Test
    void get() {
        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        User user= repo.get(1L);
        assertEquals(new User(1L,"a",new Persone(1L,"","")),user);
        repo.closeConnection();
    }

    @Test
    void getNull() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        User user= repo.get(10L);
        assertEquals(user,null);
        repo.closeConnection();
    }

    @Test
    void update() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        try{
            assertTrue(
                    repo.update(1L,new User(5L,"acadea",new Persone(2L,"weew","erui"))));
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

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        try{
            assertTrue(repo.update(7L,new User(5L,"acadea",new Persone(2L,"weew","erui"))));
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

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

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

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

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

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        List<User> list=repo.getAll();
        List<User> comparableList= Arrays.asList(new User(1L,"a",new Persone(1L,"wewe","weew")),
                new User(2L,"biscuit",new Persone(1L,"wewe","weew")),
                new User(3L,"macaron",new Persone(1L,"wewe","weew")));
        assertEquals(list,comparableList);
        repo.closeConnection();
    }


    @Test
    void getByOther() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        try{
            assertEquals(repo.getByOther("biscuit"),new User(2L,"biscuit",new Persone(1L,"wewe","weew")));
        }catch(Exception e){
            assertTrue(false);
        }
        repo.closeConnection();
    }

    @Test
    void getByOtherInexistent() {

        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);

        try{
            assertEquals(repo.getByOther("hjer"),null);
        }catch(Exception e){
            assertTrue(false);
        }
        repo.closeConnection();
    }

    @Test
    void getAllPage(){
        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        repo.save(new User(4L,"b",new Persone(1L,"wewe","weew")));
        repo.save(new User(5L,"c",new Persone(1L,"wewe","weew")));
        repo.save(new User(6L,"d",new Persone(1L,"wewe","weew")));
        repo.save(new User(7L,"e",new Persone(1L,"wewe","weew")));
        repo.save(new User(8L,"f",new Persone(1L,"wewe","weew")));
        repo.save(new User(9L,"g",new Persone(1L,"wewe","weew")));
        repo.save(new User(10L,"h",new Persone(1L,"wewe","weew")));
        repo.save(new User(11L,"i",new Persone(1L,"wewe","weew")));
        repo.save(new User(12L,"j",new Persone(1L,"wewe","weew")));
        repo.save(new User(13L,"k",new Persone(1L,"wewe","weew")));
        repo.save(new User(14L,"l",new Persone(1L,"wewe","weew")));
        repo.save(new User(15L,"m",new Persone(1L,"wewe","weew")));

        Pageble<User> pageble= new Pageble<>(0,5);
        Page<User> pageUser= repo.getAll(pageble);
        List<User> listUser=pageUser.getPageContent().collect(Collectors.toList());
        assertEquals(listUser.size(),5);
        assertEquals(listUser.get(0),new User(1L,"a",new Persone(1L,"","")));
        assertEquals(listUser.get(4),new User(5L,"c",new Persone(1L,"","")));

        pageble= (Pageble<User>) pageUser.getNextPage();
        pageUser=repo.getAll(pageble);
        listUser=pageUser.getPageContent().collect(Collectors.toList());
        assertEquals(listUser.size(),5);
        assertEquals(listUser.get(0),new User(6L,"d",new Persone(1L,"","")));
        assertEquals(listUser.get(4),new User(10L,"h",new Persone(1L,"","")));
        repo.closeConnection();
    }

    @Test
    void paging(){
        UserRepo repo = new UserDbRepo(Connections.URL, Connections.Username, Connections.Password);
        repo.save(new User(4L,"b",new Persone(1L,"wewe","weew")));
        repo.save(new User(5L,"c",new Persone(1L,"wewe","weew")));
        repo.save(new User(6L,"d",new Persone(1L,"wewe","weew")));
        repo.save(new User(7L,"e",new Persone(1L,"wewe","weew")));
        repo.save(new User(8L,"f",new Persone(1L,"wewe","weew")));
        repo.save(new User(9L,"g",new Persone(1L,"wewe","weew")));
        repo.save(new User(10L,"h",new Persone(1L,"wewe","weew")));
        repo.save(new User(11L,"i",new Persone(1L,"wewe","weew")));
        repo.save(new User(12L,"j",new Persone(1L,"wewe","weew")));
        repo.save(new User(13L,"k",new Persone(1L,"wewe","weew")));
        repo.save(new User(14L,"l",new Persone(1L,"wewe","weew")));
        repo.save(new User(15L,"m",new Persone(1L,"wewe","weew")));

        Page<User> pageUser= repo.getCurrentPage();
        List<User> listUser=pageUser.getPageContent().collect(Collectors.toList());
        assertEquals(listUser.size(),10);
        assertEquals(listUser.get(0),new User(1L,"a",new Persone(1L,"","")));
        assertEquals(listUser.get(9),new User(10L,"h",new Persone(1L,"","")));

        pageUser=repo.getNextPage();
        listUser=pageUser.getPageContent().collect(Collectors.toList());
        assertEquals(listUser.size(),5);
        assertEquals(listUser.get(0),new User(11L,"i",new Persone(1L,"","")));
        assertEquals(listUser.get(4),new User(15L,"m",new Persone(1L,"","")));

        pageUser=repo.getPreviousPage();
        listUser=pageUser.getPageContent().collect(Collectors.toList());
        assertEquals(listUser.size(),10);
        assertEquals(listUser.get(0),new User(1L,"a",new Persone(1L,"","")));
        assertEquals(listUser.get(9),new User(10L,"h",new Persone(1L,"","")));
        repo.closeConnection();
    }


}