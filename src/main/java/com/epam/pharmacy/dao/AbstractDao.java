package com.epam.pharmacy.dao;

import com.epam.pharmacy.dao.connection.ConnectionPool;
import com.epam.pharmacy.dao.connection.ConnectionPoolException;
import com.epam.pharmacy.dao.connection.ConnectionPoolImpl;
import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.Entity;
import com.epam.pharmacy.exception.ApplicationException;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.*;

public interface AbstractDao<T extends Entity> {
    boolean create(T entity, String password) throws DaoException;

    T findById(int id) throws DaoException;

    boolean update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;

    default boolean executeQuery(String query) throws DaoException {
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

    default boolean executeQuery(String query, List<Object> params) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setQueryParams(params, preparedStatement);
            result = preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("error execute", e);
        }
        return result;
    }

    default boolean executeQueryTransaction(String query, List<Object> params, Connection connection) throws DaoException {
        boolean result;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setQueryParams(params, preparedStatement);
            result = preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException("error execute", e);
        }
        return result;
    }

    default void setQueryParams(List<Object> params, PreparedStatement preparedStatement) throws SQLException {
        if (params != null && !params.isEmpty()) {
            int i = 1;
            for (Object param : params) {
                preparedStatement.setObject(i, param);
                i++;
            }
        }
    }

    default ResultSetWrapper executeQueryResult(String query) throws DaoException {
        ResultSetWrapper result;

        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            result = new ResultSetWrapper(resultSet);

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("error execute query", e);
        }
        return result;
    }


    default ResultSetWrapper executeQueryResult(String query, List<Object> params) throws DaoException {
        ResultSetWrapper result;
        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int i = 1;
            for (Object param : params) {
                preparedStatement.setObject(i, param);
                i++;
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            result = new ResultSetWrapper(resultSet);

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(" ", e);
        }
        return result;
    }

    default Connection startTransaction() throws DaoException {
        try {
            ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
            Connection connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException("error start transaction", e);
        }
    }

    default boolean commitTransaction (Connection connection) throws DaoException {
        try {
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException("error rollback transaction", e);
            }
        } finally {
            finalizeTransaction(connection);
        }
        return false;
    }

    default void finalizeTransaction (Connection connection) throws DaoException {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DaoException("set autocommit 'true' failed", e);
        }
    }

}
