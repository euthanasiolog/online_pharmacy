package com.epam.pharmacy.util.exception;

public class ProjectException extends Exception {
    public ProjectException(String message, Exception e){
        super(message, e);
    }
}
