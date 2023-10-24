package com.example.Controller.Validator;

import com.example.Domain.Message;
import com.example.Domain.User;

import com.example.Utils.Exceptions.MessageException;
import com.example.Utils.Exceptions.UserException;
import com.example.Utils.Exceptions.*;
import com.example.Utils.Exceptions.Exception;

public class MessageValidator extends AbstractValidator<Message> {
    public MessageValidator() {
    }

    @Override
    public boolean validate(Message entity) {
        listOfErrors=new String();
        try{
            if(entity.getId()!=null)checkId(entity.getId());
        }catch(Exception e){
            listOfErrors+=e.getDescription();
        }
        try{
            checkUserName(entity.getFrom().getUsername());
        }catch(UserException e){
            listOfErrors+=e.getDescription();
        }
        for(User user:entity.getReceivers()){
            try{
                checkUserName(user.getUsername());
            }catch(UserException e){
                listOfErrors+=user.getUsername()+" "+e.getDescription();
            }
        }
        if(!listOfErrors.isEmpty()) throw new MessageException(listOfErrors);
        return true;
    }
}
