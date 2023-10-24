package com.example.Utils.Exceptions;

public class EntityException extends Exception{
    public EntityException( String description) {
        super(7, description);
    }

    public EntityException(int code, String description) {
        super(code, description);
    }


}
