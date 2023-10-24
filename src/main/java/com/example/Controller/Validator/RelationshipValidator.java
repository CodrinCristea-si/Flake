package com.example.Controller.Validator;

import com.example.Domain.Relationship;
import com.example.Utils.Exceptions.RelationshipException;
import com.example.Utils.Exceptions.*;
import com.example.Utils.Exceptions.Exception;

public class RelationshipValidator extends AbstractValidator<Relationship>{
    public RelationshipValidator() {
        listOfErrors=new String();
    }

    /**
     * Checks is a ralationship has valid components
     * @param entity the object to be verified
     * @return true if it is valid
     * Throws RelationshipException if the usernames/id are not valid
     */
    @Override
    public boolean validate(Relationship entity) {
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
            checkUserName(entity.getFirstUserName());
            checkUserName(entity.getSecondUserName());
        }
        catch (Exception e){
            listOfErrors+=e.getDescription();
        }
        //System.out.println(listOfErrors);
        if(!listOfErrors.isEmpty()) throw new RelationshipException(listOfErrors);
        return true;
    }
}
