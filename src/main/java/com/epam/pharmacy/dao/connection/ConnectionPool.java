package com.epam.pharmacy.dao.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface ConnectionPool {

    Connection getConnection();

    void closeConnection (Connection connection, PreparedStatement statement, ResultSet resultSet);

    void closeConnection (Connection connection, PreparedStatement statement);

    void closePool ();

}
