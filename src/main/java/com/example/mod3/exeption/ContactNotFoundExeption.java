package com.example.mod3.exeption;


public class ContactNotFoundExeption extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ContactNotFoundExeption(String message) {
        super(message);
    }

    public ContactNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactNotFoundExeption(Throwable cause) {
        super(cause);
    }
}

