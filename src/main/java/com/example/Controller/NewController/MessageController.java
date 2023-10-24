package com.example.Controller.NewController;

import com.example.Domain.Message;
import com.example.Domain.User;
import com.example.Repository.Db.MessageDbRepo;

import java.util.ArrayList;
import java.util.List;

public class MessageController extends Controller<Long, Message>{
    private MessageDbRepo repoM;
    public MessageController(MessageDbRepo repo){super.repo=repo;
    repoM=repo;}

    public List<Message> loadConversation(String username1,String username2){
        List<Message> listMess= repoM.getBySR(username1,username2);
        for(var el:repoM.getBySR(username2,username1))
            listMess.add(el);
        return listMess;
    }

    public List<Message> getAll(){
        return repo.getAll();
    }

    public int deleteAllMessagesByUsername(String username){
        List<Long> listId= new ArrayList<>();
        for(Message mess: (List<Message>) repo.getAll()){
            if(mess.getFrom().getUsername().equals(username)){
                if(!listId.contains(mess.getId()))
                    listId.add(mess.getId());
            }
            else {
                for (User user: mess.getReceivers())
                    if(user.getUsername().equals(username)){
                        if(!listId.contains(mess.getId()))
                            listId.add(mess.getId());
                    }
            }
        }
        for(Long el: listId){
            repo.delete(el);
        }
        return listId.size();
    }
}
