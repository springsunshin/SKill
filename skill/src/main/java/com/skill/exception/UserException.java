package com.skill.exception;


public class UserException extends RuntimeException{

    private String message;
    public UserException(){}

    public UserException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
