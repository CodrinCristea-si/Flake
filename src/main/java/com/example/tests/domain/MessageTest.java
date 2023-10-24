package com.example.tests.domain;

import com.example.Domain.Message;
import com.example.Domain.Persone;
import com.example.Domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MessageTest {

    @Test
    void Ids(){
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2));
        message.setId(1L);
        assertEquals(message.getId(),1L);
    }

    @Test
    void getFrom() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2));
        assertEquals(message.getFrom(),user1);
    }

    @Test
    void setFrom() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        User user3= new User("haha",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2));
        message.setFrom(user3);
        assertEquals(message.getFrom(),user3);
    }

    @Test
    void getMessage() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2));
        assertEquals(message.getMessage(),"hello");
    }

    @Test
    void setMessage() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2));
        message.setMessage("salut");
        assertEquals(message.getMessage(),"salut");
    }

    @Test
    void getReceivers() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2));
        assertEquals(message.getReceivers(),Arrays.asList(user2));
    }

    @Test
    void setReceivers() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        User user3= new User("haha",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2));
        message.setReceivers(Arrays.asList(user3));
        assertEquals(message.getReceivers(),Arrays.asList(user3));
    }

    @Test
    void getDate() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        User user3= new User("haha",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2), LocalDateTime.of(2021,11,19,12,11));
        assertEquals(message.getDate().toString(),"2021-11-19T12:11");

    }

    @Test
    void setDate() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2), LocalDateTime.of(2021,11,19,12,11));
        message.setDate(LocalDateTime.of(2021,11,19,12,12));
        assertEquals(message.getDate().toString(),"2021-11-19T12:12");
    }

    @Test
    void getReply() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        User user3= new User("haha",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2), LocalDateTime.of(2021,11,19,12,11));
        Message message1= new Message(user1,"hey",Arrays.asList(user3),LocalDateTime.of(2021,11,19,12,11),message);
        assertEquals(message1.getReply(),message);
    }

    @Test
    void setReply() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        User user3= new User("haha",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2), LocalDateTime.of(2021,11,19,12,11));
        Message message1= new Message(user1,"hey",Arrays.asList(user3),LocalDateTime.of(2021,11,19,12,11),message);
        Message message2= new Message(new User("habulea",new Persone("maca","baka")), "how are u",Arrays.asList(user2));
        message1.setReply(message2);
        assertEquals(message1.getReply(),message2);
    }

    @Test
    void testEquals() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        User user3= new User("haha",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2), LocalDateTime.of(2021,11,19,12,11));
        Message message1= new Message(user1,"hello",Arrays.asList(user2),LocalDateTime.of(2021,11,19,12,11));
        assertEquals(message,message1);

    }

    @Test
    void testNotEquals() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        User user3= new User("haha",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2), LocalDateTime.of(2021,11,19,12,11));
        Message message1= new Message(user1,"hey",Arrays.asList(user3),LocalDateTime.of(2021,11,19,12,11),message);
        Message message2= new Message(new User("habulea",new Persone("maca","baka")), "how are u",Arrays.asList(user2));
        assertNotEquals(message,message2);
    }

    @Test
    void testToString() {
        User user1= new User("habulea",new Persone("maca","baka"));
        User user2= new User("bila",new Persone("mac","bak"));
        Message message= new Message(new User("habulea",new Persone("maca","baka")), "hello",Arrays.asList(user2));
        assertEquals(message.toString(),"Message{from=User{username='habulea', pers=Persone{firstName='maca', lastName='baka'}}," +
                " message='hello', " +
                "receivers=[User{username='bila', pers=Persone{firstName='mac', lastName='bak'}}]," +
                " date=null, reply=null}");
    }
}