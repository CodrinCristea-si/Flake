package com.example.Controller.Validator;

import com.example.Domain.Persone;

public class PersoneValidator extends AbstractValidator<Persone> {

    public PersoneValidator(){
        listOfErrors=new String();
    }

    /**
     * Checks if the names of a persone are valid
     * @param entity the object to be verified
     * @return true if the persone is valid, false otherwise
     */
    @Override
    public boolean validate(Persone entity) {
        checkName(entity.getFirstName());
        checkName(entity.getLastName());
        return true;
    }


}
