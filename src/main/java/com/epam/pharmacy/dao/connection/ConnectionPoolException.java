package com.epam.pharmacy.dao.connection;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException(String message, Exception e){
        super(message, e);
    }
}
