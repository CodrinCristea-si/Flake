package com.example.tests.repository.memory;

import com.example.Domain.Relationship;
import com.example.Repository.Memory.MemoryRepo;
import com.example.Repository.Memory.RelationshipMemoryRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipMemoryRepoTest {

    @Test
    void save() {
        MemoryRepo repo= new RelationshipMemoryRepo();
        Relationship rel = new Relationship("acadea","bucales");
        assertTrue(repo.save(rel));
        assertTrue(rel.getId()!=null);

        Relationship rel1 = new Relationship(1L,"acadea","bucales");
        assertFalse(repo.save(rel1));

        Relationship rel2 = new Relationship(rel.getId(),"ochelari","bucales");
        assertFalse(repo.save(rel2));
    }

    @Test
    void get() {
        MemoryRepo repo= new RelationshipMemoryRepo();
        Relationship rel = new Relationship("acadea","bucales");
        Relationship rel2 = new Relationship(1L,"ochelari","bucales");
        repo.save(rel);
        repo.save(rel2);
        assertTrue(repo.get(rel.getId()).getId()==rel.getId());
        assertTrue(repo.get(-5)==null);
    }

    @Test
    void update() {
        MemoryRepo repo= new RelationshipMemoryRepo();
        Relationship rel = new Relationship("acadea","bucales");
        Relationship rel2 = new Relationship(1L,"ochelari","bucales");
        repo.save(rel);
        assertTrue(repo.update(rel.getId(),rel2));
        assertTrue(repo.get(rel.getId())==null);
        assertEquals(repo.get(rel2.getId()),rel2);
    }

    @Test
    void delete() {
        MemoryRepo repo= new RelationshipMemoryRepo();
        Relationship rel = new Relationship("acadea","bucales");
        Relationship rel2 = new Relationship(1L,"ochelari","bucales");
        repo.save(rel);
        repo.save(rel2);
        assertTrue(repo.delete(rel.getId()));
        assertTrue(repo.get(rel.getId())==null);
        assertTrue(repo.getSize()==1);

        assertFalse(repo.delete(rel.getId()));

        assertTrue(repo.delete(rel2.getId()));
        assertTrue(repo.getSize()==0);
    }

    @Test
    void getAll() {
        MemoryRepo repo= new RelationshipMemoryRepo();
        Relationship rel = new Relationship("acadea","bucales");
        Relationship rel2 = new Relationship(1L,"ochelari","bucales");
        repo.save(rel);
        repo.save(rel2);

        assertEquals(repo.getAll().size(),2);
        assertEquals(repo.getAll().get(0),rel);
        assertEquals(repo.getAll().get(1),rel2);

        repo.delete(rel.getId());
        assertEquals(repo.getAll().size(),1);
        assertEquals(repo.getAll().get(0),rel2);
    }

    @Test
    void getByOther() {
        MemoryRepo repo= new RelationshipMemoryRepo();
        Relationship rel = new Relationship("acadea","bucales");
        Relationship rel2 = new Relationship(1L,"ochelari","bucales");
        repo.save(rel);
        repo.save(rel2);

        assertEquals(repo.getByOther("acadea","bucales"),rel);
        assertEquals(repo.getByOther("ochelari","bucales"),rel2);
        assertEquals(repo.getByOther("awee","bucales"),null);
    }
}