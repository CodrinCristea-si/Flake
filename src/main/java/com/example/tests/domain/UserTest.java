package com.example.tests.domain;

import com.example.Domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getUsername() {
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        assertTrue(user.getUsername().equals("acadea"));
    }

    @Test
    void setUsername() {
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        user.setUsername("babushka");
        assertTrue(user.getUsername().equals("babushka"));
    }

    @Test
    void getPers() {
        User user= new User("acadea", new Persone("Vasile","Purcel"));
        Persone pers=user.getPers();
        assertTrue(pers.getFirstName().equals("Vasile"));
        assertTrue(pers.getLastName().equals("Purcel"));
    }

    @Test
    void setPers() {
        User user= new User("acadea", new Persone("True","Vasile"));
        Persone pers2= new Persone ("Fake","Vasile");
        user.setPers(pers2);
        Persone pers=user.getPers();
        assertTrue(pers.getFirstName().equals("Fake"));
        assertTrue(pers.getLastName().equals("Vasile"));
    }

    @Test
    void testToString() {
        User user= new User("acadea", new Persone("True","Vasile"));
        assertTrue(user.toString().equals("User{" +
                "username='" + "acadea" + '\'' +
                ", pers=" + "Persone{"+
                "firstName='" + "True" + '\'' +
                ", lastName='" + "Vasile" + '\'' +
                '}' +
                '}'));
        user.setId(1L);
        assertTrue(user.toString().equals("User{" +
                "id" +"1"+
                "username='" + "acadea" + '\'' +
                ", pers=" + "Persone{"+
                "firstName='" + "True" + '\'' +
                ", lastName='" + "Vasile" + '\'' +
                '}' +
                '}'));
    }

    @Test
    void getId(){
        User user= new User(1L,"acadea", new Persone("True","Vasile"));
        assertTrue(user.getId()==1L);
    }

    @Test
    void setId(){
        User user= new User(1L,"acadea", new Persone("True","Vasile"));
        user.setId(2L);
        assertTrue(user.getId()==2L);
    }

    @Test
    void testEquals(){
        User user1= new User(1L,"acadea", new Persone("True","Vasile"));
        User user2= new User(1L,"maslin", new Persone("Fake","Vasile"));
        assertTrue(user1.equals(user2));

        User user3= new User(2L,"acadea", new Persone("Fake","Vasile"));
        assertTrue(user1.equals(user3));

        User user4= new User(2L,"maslin", new Persone("True","Vasile"));
        assertFalse(user1.equals(user4));
    }

    @Test
    void addFriend(){
        User user1= new User(1L,"acadea", new Persone("True","Vasile"));
        User user2= new User(2L,"maslin", new Persone("Fake","Vasile"));

        user1.addFriend(user2.getUsername());
        assertTrue(user1.getNumberOfFriends()==1);
        assertTrue(user2.getNumberOfFriends()==0);
    }

    @Test
    void removeFriend(){
        User user1= new User(1L,"acadea", new Persone("True","Vasile"));
        User user2= new User(2L,"maslin", new Persone("Fake","Vasile"));

        user1.addFriend(user2.getUsername());
        user1.removeFriend(user2.getUsername());
        assertTrue(user1.getNumberOfFriends()==0);
        user1.removeFriend(user2.getUsername());
        assertTrue(user1.getNumberOfFriends()==0);
    }

    @Test
    void getAllFriends() {
        User user1= new User(1L,"acadea", new Persone("True","Vasile"));
        User user2= new User(2L,"maslin", new Persone("Fake","Vasile"));
        User user3= new User(3L,"merisor", new Persone("All","Vasile"));

        user1.addFriend(user2.getUsername());
        assertTrue(user1.getFriendsList().get(0).equals("maslin"));

        user1.addFriend(user3.getUsername());
        assertTrue(user1.getFriendsList().get(0).equals("maslin"));
        assertTrue(user1.getFriendsList().get(1).equals("merisor"));

        user1.removeFriend(user2.getUsername());
        assertTrue(user1.getFriendsList().get(0).equals("merisor"));

        user1.removeFriend(user3.getUsername());
        assertTrue(user1.getFriendsList().size()==0);
    }
}