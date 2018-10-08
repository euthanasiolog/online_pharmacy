package com.epam.pharmacy.dao;

import com.epam.pharmacy.dao.connection.ConnectionPool;
import com.epam.pharmacy.dao.connection.ConnectionPoolException;
import com.epam.pharmacy.dao.connection.ConnectionPoolImpl;
import com.epam.pharmacy.model.Entity;
import com.epam.pharmacy.exception.ApplicationException;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.*;

public interface AbstractDao<T extends Entity> {
    boolean create(T entity, String password) throws DaoException;
    T findById(int id) throws DaoException;
    boolean update(T entity) throws DaoException;

    default boolean delete (T entity) throws DaoException {
        int id = entity.getId();
        List<Object> param = new ArrayList<>();
        param.add(id);
        return executeQuery("UPDATE user SET `archive`='1' WHERE id=?", param);
    }

    default boolean executeQuery (String query) throws DaoException {
        boolean result;
        try {
            ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            result = statement.execute(query);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("error execute query", e);
        }
        return result;
    }

    default boolean executeQuery (String query, List<Object> params) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            int i = 1;
            for (Object param:params){
                preparedStatement.setObject(i, param);
                i++;
            }
            result = preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e){
            throw new DaoException("error execute", e);
        }
        return result;
    }

    default ResultSet executeQueryResult(String query) throws DaoException {
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection();
              Statement statement = connection.createStatement()){
            resultSet = statement.executeQuery(query);
        } catch (SQLException | ConnectionPoolException e){
            throw new DaoException("error execute query", e);
        }
        return resultSet;
    }

    default ResultSet executeQueryResult (String query, List<Object> params) throws DaoException {
        ResultSet resultSet;
        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            int i = 1;
            for (Object param:params){
                preparedStatement.setObject(i, param);
                i++;
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(" ", e);
        }
        return resultSet;
    }

    default boolean executeTransaction (String...query) throws DaoException {
        Connection connection = null;
        List<PreparedStatement> statements = new ArrayList<>();
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                for (String q:query){
                    statements.add(connection.prepareStatement(q));
                }
                for (PreparedStatement p:statements){
                    p.execute();
                }
                connection.commit();
                return true;
            }
        } catch (SQLException | ConnectionPoolException e) {
            if (connection!=null){
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new DaoException("error rollback transaction", e);
                }
            }
        } finally {
            finalizeTransaction(statements, connection);
        }
        return false;
    }

    default boolean executeTransaction (Map<String, List<Object>> executing) throws DaoException {
        Connection connection = null;
        Set<Map.Entry<String, List<Object>>> executingStatements = new HashSet<>();
        List<PreparedStatement> statements = new ArrayList<>();
        try {
            executingStatements = executing.entrySet();
            connection = ConnectionPoolImpl.getInstance().getConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                for (Map.Entry<String, List<Object>> entry:executingStatements){
                    PreparedStatement preparedStatement = connection.prepareStatement(entry.getKey());
                    List<Object> objects = entry.getValue();
                    int i = 1;
                    for (Object param:objects){
                        preparedStatement.setObject(i, param);
                        i++;
                    }
                }
                for (PreparedStatement p:statements){
                    p.execute();
                }
                connection.commit();
                return true;
            }
        } catch (SQLException | ConnectionPoolException e) {
            if (connection!=null){
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    throw new DaoException("error rollback transaction", e);
                }
            }
        } finally {
            finalizeTransaction(statements, connection);
        }
        return false;
    }

    static void finalizeTransaction (List<PreparedStatement> statements, Connection connection) throws DaoException {
        try {
            for (PreparedStatement p:statements){
                p.close();
            }
            if (connection!=null){
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DaoException("set autocommit 'true' failed", e);
        }
    }

}
