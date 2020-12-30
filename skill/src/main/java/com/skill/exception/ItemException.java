package com.skill.exception;

public class ItemException extends RuntimeException {
    private String message;


    public ItemException(){}
    public ItemException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
