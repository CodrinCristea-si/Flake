package com.example.Repository;

import com.example.Domain.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepo extends Repository<Long,Message>{

    List<Message> getBySR(String sender, String Receiver);

    List<Message> getByDateTime(LocalDateTime dateTime);
}
