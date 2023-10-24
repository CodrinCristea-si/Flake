package com.example.Utils.Exceptions;

public class PersoneException extends EntityException{
    public PersoneException( String description) {
        super(1, description);
    }
    public PersoneException( int code,String description) {
        super(code, description);
    }
}
