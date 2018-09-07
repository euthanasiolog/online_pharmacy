package dao;

import connection.ConnectionPool;
import connection.ConnectionPoolException;
import entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class AbstractDao {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    public abstract void create(Entity entity);
    public abstract void getById(long id);
    public abstract void update(Entity entity);
    public abstract void delete(Entity entity);

    protected Connection getConnection(){
        try {
            return connectionPool.takeConnection();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void closeConnection(Connection connection, PreparedStatement statement){
        connectionPool.closeConnection(connection, statement);
    }

    protected void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet){
        connectionPool.closeConnection(connection, statement, resultSet);
    }

}
