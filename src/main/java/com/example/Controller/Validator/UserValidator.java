package com.example.Controller.Validator;

import com.example.Domain.User;
import com.example.Utils.Exceptions.Exception;
import com.example.Utils.Exceptions.PersoneException;
import com.example.Utils.Exceptions.UserException;

public class UserValidator extends AbstractValidator<User>{

    public UserValidator(){
    }

    /**
     * Checks is an user has valid components
     * @param entity the object to be verified
     * @return true if it is valid
     * @throws UserException if the usernames/id/names of the person are not valid
     */
    @Override
    public boolean validate(User entity) throws UserException {
        listOfErrors=new String();
        try{
            if(entity.getId()!=null){
                checkId(entity.getId());
            }
        }
        catch (Exception e){
            listOfErrors+=e.getDescription();
        }
        try{
            checkName(entity.getPers().getFirstName());
            checkName(entity.getPers().getLastName());
        }
        catch (PersoneException e){
            listOfErrors+=e.getDescription();
        }
        try{
            checkPassword(entity.getPassword());
        }
        catch (Exception e){
            listOfErrors+=e.getDescription();
        }
        try{
            checkUserName(entity.getUsername());
        }
        catch (Exception e){
            listOfErrors+=e.getDescription();
        }
        if(!listOfErrors.isEmpty()) throw new UserException(listOfErrors);
        return true;
    }

}
