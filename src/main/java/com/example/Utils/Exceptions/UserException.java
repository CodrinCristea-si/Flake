package com.example.Utils.Exceptions;

public class UserException extends EntityException{
    public UserException(String description) {
        super(2,description);
    }
    public UserException(int code,String description) {
        super(code,description);
    }
}
