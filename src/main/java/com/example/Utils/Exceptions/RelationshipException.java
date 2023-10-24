package com.example.Utils.Exceptions;

public class RelationshipException extends EntityException{
    public RelationshipException(String description) {
        super(3,description);
    }
    public RelationshipException(int code,String description) {
        super(code,description);
    }
}
