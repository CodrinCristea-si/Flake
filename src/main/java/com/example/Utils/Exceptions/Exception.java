package com.example.Utils.Exceptions;

public class Exception extends RuntimeException{
    protected int code;
    protected String description;

    public Exception(int code,String description){
        this.code=code;
        this.description=description;
    }

    public Exception(String description){
        this.description=description;
    }

    /**
     * Gives the code of the exception
     * @return the code of the exception
     */
    public int getCode(){
        return code;
    }

    /**
     * Gives the description of the exception
     * @return the description of the exception
     */
    public String getDescription(){
        return description;
    }
}
