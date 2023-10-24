package com.example.tests.repository.memory;


import com.example.Repository.Memory.MemoryRepo;
import com.example.Repository.Memory.UserMemoryRepo;
import org.junit.jupiter.api.Test;
import com.example.Domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMemoryRepoTest {

    @Test
    void save() {
        MemoryRepo repo= new UserMemoryRepo();
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        assertTrue(repo.save(user));

        Long id= user.getId();
        user= new User(id,"acadea", new Persone("Vasile","Purcel"));
        assertFalse(repo.save(user));

        assertEquals(repo.getSize(),1);

        User user1= new User(1L,"acadea", new Persone("Vasile","Purcel"));
        assertFalse(repo.save(user1));
    }

    @Test
    void get() {
        MemoryRepo repo= new UserMemoryRepo();
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        assertTrue(repo.save(user));
        Long id= user.getId();
        User user1= (User)repo.get(id);
        assertEquals(user,user1);

        assertEquals(repo.get(-7),null);
    }

    @Test
    void update() {
        MemoryRepo repo= new UserMemoryRepo();
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        repo.save(user);
        User user1= new User("babele", new Persone("Claudiu","Stefan"));
        assertTrue(repo.update(repo.getByOther("acadea").getId(),user1));

        assertEquals(repo.get(user.getId()),null);
        assertEquals(repo.get(user1.getId()),user1);
    }

    @Test
    void delete() {
        MemoryRepo repo= new UserMemoryRepo();
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        User user1= new User("babele", new Persone("Claudiu","Stefan"));
        repo.save(user);
        repo.save(user1);
        assertTrue(repo.delete(user.getId()));
        assertTrue(repo.get(user.getId())==null);

        assertFalse(repo.delete(user.getId()));
        assertEquals(repo.getSize(),1);

        assertTrue(repo.delete(user1.getId()));
        assertEquals(repo.getSize(),0);
    }


    @Test
    void getAll() {
        MemoryRepo repo= new UserMemoryRepo();
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        User user1= new User("babele", new Persone("Claudiu","Stefan"));
        User user2= new User("gigilica", new Persone("Claudiu","Stefan"));
        repo.save(user);
        repo.save(user1);
        repo.save(user2);
        List<User>list =repo.getAll();
        assertEquals(list.size(),3);
        assertEquals(list.get(0),user);
        assertEquals(list.get(1),user1);
        assertEquals(list.get(2),user2);
    }

    @Test
    void getAllIds() {
        MemoryRepo repo= new UserMemoryRepo();
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        User user1= new User("babele", new Persone("Claudiu","Stefan"));
        User user2= new User("gigilica", new Persone("Claudiu","Stefan"));
        repo.save(user);
        repo.save(user1);
        repo.save(user2);
        List<Long>listId= repo.getAllIds();
        assertEquals(listId.size(),3);
        assertEquals(listId.get(0),user.getId());
        assertEquals(listId.get(1),user1.getId());
        assertEquals(listId.get(2),user2.getId());
    }

    @Test
    void getByOther() {
        MemoryRepo repo= new UserMemoryRepo();
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        User user1= new User("babele", new Persone("Claudiu","Stefan"));
        User user2= new User("gigilica", new Persone("Claudiu","Stefan"));
        repo.save(user);
        repo.save(user1);
        repo.save(user2);
        assertEquals(repo.getByOther("acadea"),user);
        assertEquals(repo.getByOther("gigilica"),user2);
        assertEquals(repo.getByOther("banana"),null);
    }
}