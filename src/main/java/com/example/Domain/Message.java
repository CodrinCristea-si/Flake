package com.example.Domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Message extends Entity<Long>{
    private User from= null;
    private String message= null;
    private List<User> receivers = null;
    private LocalDateTime date= null;
    private Message reply= null;

    public Message(User from, String message, List<User> receivers, LocalDateTime date, Message reply) {
        this.from = from;
        this.message = message;
        this.receivers = receivers;
        this.date = date;
        this.reply = reply;
    }
    public Message(Long id,User from, String message, List<User> receivers) {
        this.from = from;
        this.message = message;
        this.receivers = receivers;
        super.setId(id);
    }

    public Message(Long id,User from, String message, List<User> receivers, LocalDateTime date, Message reply) {
        this.from = from;
        this.message = message;
        this.receivers = receivers;
        this.date = date;
        this.reply = reply;
        setId(id);
    }

    public Message(User from, String message, List<User> receivers, LocalDateTime date) {
        this.from = from;
        this.message = message;
        this.receivers = receivers;
        this.date = date;
    }

    public Message(Long id,User from, String message, List<User> receivers, LocalDateTime date) {
        this.from = from;
        this.message = message;
        this.receivers = receivers;
        this.date = date;
        super.setId(id);
    }

    public Message(User from, String message, List<User> receivers) {
        this.from = from;
        this.message = message;
        this.receivers = receivers;

    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Message getReply() {
        return reply;
    }

    public void setReply(Message reply) {
        this.reply = reply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(from, message1.from) && Objects.equals(message, message1.message) && Objects.equals(receivers, message1.receivers) && Objects.equals(date, message1.date) && Objects.equals(reply, message1.reply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, message, receivers, date, reply);
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", message='" + message + '\'' +
                ", receivers=" + receivers +
                ", date=" + date +
                ", reply=" + reply +
                '}';
    }
}
