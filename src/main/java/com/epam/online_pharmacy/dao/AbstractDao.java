package com.epam.online_pharmacy.dao;

import com.epam.online_pharmacy.connection.ConnectionPool;
import com.epam.online_pharmacy.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface AbstractDao {
    void create(Entity entity);
    Entity findById(int id);
    void update(Entity entity);
    void delete(Entity entity);

    default Connection getConnection(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        return connectionPool.takeConnection();
    }

    default void closeConnection(Connection connection, PreparedStatement statement){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.closeConnection(connection, statement);
    }

    default void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.closeConnection(connection, statement, resultSet);
    }
}
