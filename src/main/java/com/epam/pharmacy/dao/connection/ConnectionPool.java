package com.epam.pharmacy.dao.connection;

import java.sql.Connection;

public interface ConnectionPool {

    Connection getConnection();

    void closePool () throws ConnectionPoolException;

}
