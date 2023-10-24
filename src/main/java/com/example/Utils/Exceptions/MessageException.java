package com.example.Utils.Exceptions;

public class MessageException extends EntityException {
    public MessageException(int code, String description) {
        super(code, description);
    }

    public MessageException(String description) {
        super(9,description);
    }
}
