package com.example.tests.controller.old;

import com.example.Controller.OldController.Controller;
import com.example.Controller.OldController.RelationshipController;
import com.example.Domain.Relationship;
import com.example.Repository.Memory.RelationshipMemoryRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.Utils.Exceptions.*;
import com.example.Utils.Exceptions.Exception;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RelationshipControllerTest {

    @Test
    void add() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        try{
            cont.add(new Relationship(1L,"a","b"));
            assertTrue(true);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void addException() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        try{
            cont.add(new Relationship(1L,"a","b"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
        try{
            cont.add(new Relationship(2L,"a","b"));
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void removeById() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        try{
            cont.removeById(1L);
            assertTrue(cont.getSize()==1);
            assertTrue(true);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void removeByIdException() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        try{
            cont.removeById(3L);
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }
    @Test
    void removeByOthers() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        try{
            cont.removeByOthers("a","b");
            assertTrue(cont.getSize()==1);
            assertTrue(true);
        }catch(Exception e){
            assertTrue(false);
        }
    }

    @Test
    void removeByOthersException() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        Controller cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        try{
            cont.removeByOthers("a","d");
            assertTrue(false);
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @Test
    void getById() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));

        Assertions.assertEquals(cont.getById(1L).getFirstUserName(),"a");
        Assertions.assertEquals(cont.getById(1L).getSecondUserName(),"b");
        Assertions.assertEquals(cont.getById(3L),null);
    }

    @Test
    void getByOther() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));

        Assertions.assertEquals(cont.getByOther("b","c").getId(),2L);
        Assertions.assertEquals(cont.getByOther("m","n"),null);
    }

    @Test
    void getAll() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        cont.add(new Relationship(3L,"b","d"));

        Assertions.assertEquals(cont.getSize(),3);
        Assertions.assertEquals(cont.getAll().size(),3);
        Assertions.assertEquals(cont.getAll().get(0).getId(),1L);
        Assertions.assertEquals(cont.getAll().get(1).getId(),2L);
        Assertions.assertEquals(cont.getAll().get(2).getId(),3L);
    }


    @Test
    void deleteAllRelationsByUsername() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        cont.add(new Relationship(1L,"a","b"));
        cont.add(new Relationship(2L,"b","c"));
        cont.add(new Relationship(3L,"b","d"));
        Assertions.assertEquals(cont.deleteAllRelationsByUsername("b"),3);
        Assertions.assertEquals(cont.getSize(),0);
        Assertions.assertEquals(cont.deleteAllRelationsByUsername("a"),0);
    }

    @Test
    void getNumberOfCommunities() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        repo.save(new Relationship(1L,"a","b"));
        repo.save(new Relationship(2L,"b","c"));
        repo.save(new Relationship(3L,"b","d"));
        repo.save(new Relationship(4L,"c","d"));
        repo.save(new Relationship(5L,"c","e"));
        repo.save(new Relationship(6L,"e","f"));
        repo.save(new Relationship(7L,"g","h"));
        repo.save(new Relationship(8L,"i","j"));
        repo.save(new Relationship(9L,"i","k"));
        repo.save(new Relationship(10L,"j","k"));

        Assertions.assertEquals(
                cont.getNumberOfCommunities(11),3);
        cont.removeById(7L);
        Assertions.assertEquals(cont.getNumberOfCommunities(11),4);

    }

    @Test
    void getTheMostSociableCommunity() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        repo.save(new Relationship(1L,"a","b"));
        repo.save(new Relationship(2L,"b","c"));
        repo.save(new Relationship(3L,"b","d"));
        repo.save(new Relationship(4L,"c","d"));
        repo.save(new Relationship(5L,"c","e"));
        repo.save(new Relationship(6L,"e","f"));
        repo.save(new Relationship(7L,"g","h"));
        repo.save(new Relationship(8L,"i","j"));
        repo.save(new Relationship(9L,"i","k"));
        repo.save(new Relationship(10L,"j","k"));
        List<String> list=new ArrayList<>();
        list.add("a");list.add("b");list.add("c");list.add("d");list.add("e");list.add("f");
        Assertions.assertEquals(cont.getTheMostSociableCommunity(11),list);

        cont.removeById(2L);
        cont.removeById(4L);
        List<String> list1=new ArrayList<>();
        list1.add("a");list1.add("b");list1.add("d");
        Assertions.assertEquals(cont.getTheMostSociableCommunity(11),list1);
    }

    @Test
    void getTheMostSociableCommunity1() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        repo.save(new Relationship(1L,"1","2"));
        repo.save(new Relationship(2L,"2","3"));
        repo.save(new Relationship(3L,"2","4"));
        repo.save(new Relationship(4L,"3","6"));
        repo.save(new Relationship(5L,"4","5"));
        repo.save(new Relationship(6L,"5","6"));
        repo.save(new Relationship(7L,"6","7"));

        repo.save(new Relationship(11L,"11","21"));
        repo.save(new Relationship(21L,"11","31"));
        repo.save(new Relationship(31L,"11","41"));
        repo.save(new Relationship(41L,"11","51"));
        repo.save(new Relationship(51L,"11","61"));
        repo.save(new Relationship(61L,"11","71"));
        repo.save(new Relationship(71L,"11","81"));
        repo.save(new Relationship(81L,"11","91"));
        repo.save(new Relationship(91L,"11","101"));
        repo.save(new Relationship(101L,"11","111"));

        List<String> list=new ArrayList<>();
        list.add("1");list.add("2");list.add("3");list.add("4");list.add("6");list.add("5");list.add("7");
        Assertions.assertEquals(
                cont.getTheMostSociableCommunity(7)
                ,list);

        /*
        cont.removeById(2L);
        cont.removeById(4L);
        List<String> list1=new ArrayList<>();
        list1.add("a");list1.add("b");list1.add("d");
        Assertions.assertEquals(cont.getTheMostSociableCommunity(11),list1);
        */
    }

    @Test
    void getTheMostSociableCommunity2() {
        RelationshipMemoryRepo repo=new RelationshipMemoryRepo();
        RelationshipController cont=new RelationshipController(repo);
        repo.save(new Relationship(1L,"1","2"));
        repo.save(new Relationship(2L,"1","3"));
        repo.save(new Relationship(3L,"1","4"));
        repo.save(new Relationship(4L,"1","5"));
        repo.save(new Relationship(5L,"1","6"));
        repo.save(new Relationship(6L,"1","7"));
        repo.save(new Relationship(7L,"1","8"));
        repo.save(new Relationship(8L,"1","9"));
        repo.save(new Relationship(9L,"1","10"));
        repo.save(new Relationship(10L,"1","11"));
        List<String> list=new ArrayList<>();
        //list.add("1");list.add("b");list.add("c");list.add("d");list.add("e");list.add("f");
        list.add("1");list.add("2");list.add("3");list.add("4");list.add("5");list.add("6");list.add("7");
        list.add("8");list.add("9"); list.add("10"); list.add("11");
        Assertions.assertEquals(cont.getTheMostSociableCommunity(11),list);

        /*
        cont.removeById(2L);
        cont.removeById(4L);
        List<String> list1=new ArrayList<>();
        list1.add("a");list1.add("b");list1.add("d");
        Assertions.assertEquals(cont.getTheMostSociableCommunity(11),list1);
        */
    }
}