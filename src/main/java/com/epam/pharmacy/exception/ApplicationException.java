package com.epam.pharmacy.exception;

public class ApplicationException extends Exception {
    public ApplicationException(String message, Exception e){
        super(message, e);
    }
}
