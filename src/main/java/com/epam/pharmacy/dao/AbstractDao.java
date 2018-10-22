package com.epam.pharmacy.dao;

import com.epam.pharmacy.dao.connection.ConnectionPool;
import com.epam.pharmacy.dao.connection.ConnectionPoolException;
import com.epam.pharmacy.dao.connection.ConnectionPoolImpl;
import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.Entity;

import java.sql.*;
import java.util.*;

public interface AbstractDao<T extends Entity> {
    boolean create(T entity, String password) throws DaoException;

    T findById(int id) throws DaoException;

    boolean update(T entity) throws DaoException;

    boolean delete(T entity) throws DaoException;

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
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setQueryParams(params, preparedStatement);
            result = preparedStatement.execute();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("error execute", e);
        }
        return result;
    }

    default boolean executeQuery (String query, List<Object> params, Connection connection) throws DaoException {
        boolean result;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setQueryParams(params, preparedStatement);
            result = preparedStatement.execute();

        } catch (SQLException e) {
            throw new DaoException("error execute", e);
        }
        return result;
    }

    default boolean executeQuery (String query, Connection connection) throws DaoException {
        boolean result;
        try (Statement statement = connection.createStatement()) {

            result = statement.execute(query);

        } catch (SQLException e) {
            throw new DaoException("error execute", e);
        }
        return result;
    }

    default boolean executeQueryUpdate (String query, List<Object> params) throws DaoException {
        boolean result = false;
        try (Connection connection = ConnectionPoolImpl.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setQueryParams(params, preparedStatement);
            int i = preparedStatement.executeUpdate();
            if (i>0) {
                result = true;
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("error execute", e);
        }
        return result;
    }

    default boolean executeQueryUpdate (String query, List<Object> params, Connection connection) throws DaoException {
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setQueryParams(params, preparedStatement);
            int i = preparedStatement.executeUpdate();
            if (i>0) {
                result = true;
            }

        } catch (SQLException e) {
            throw new DaoException("error execute", e);
        }
        return result;
    }

    default ResultSetWrapper executeQueryResult (String query, List<Object> params, Connection connection) throws DaoException {
        ResultSetWrapper result;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setQueryParams(params, preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            result = new ResultSetWrapper(resultSet);

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
            setQueryParams(params, preparedStatement);

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
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new DaoException("set autocommit 'true' failed", e);
            }
        }
        return false;
    }

    default List<Object> putParameters (Object... params) {
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(Arrays.asList(params));
        return paramList;
    }

}
