package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.UserDao;
import com.epam.pharmacy.dao.UserDaoFactory;
import com.epam.pharmacy.dao.connection.ConnectionPool;
import com.epam.pharmacy.dao.connection.ConnectionPoolException;
import com.epam.pharmacy.dao.connection.ConnectionPoolImpl;
import com.epam.pharmacy.util.constant.Role;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Log4j2
public class UserDaoFactoryImpl implements UserDaoFactory {

    private static UserDaoFactoryImpl instance = new UserDaoFactoryImpl();

    private UserDaoFactoryImpl () {
    }

    public static UserDaoFactoryImpl getInstance () {
        if (instance==null){
            instance = new UserDaoFactoryImpl();
        }
        return instance;
    }

    @Override
    public UserDao createUserDAO(Role role) {
        switch (role){
            case CLIENT:
                return new ClientDaoImpl();
            case DOCTOR:
                return new DoctorDaoImpl();
            case PHARMACIST:
                return new PharmacistDaoImpl();
            default:
               return null;
        }
    }

    @Override
    public Role checkUserRole(String login, String password) throws DaoException {
        Connection connection;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
            connection = connectionPool.getConnection();
        } catch (ConnectionPoolException e) {
            log.error("error get connection", e);
            throw new DaoException("error get connection", e);
        }

        try {
            preparedStatement = connection.prepareStatement(SQLQueries.CHECK_USER_ROLE);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String roleStr = resultSet.getString("type");
                return Role.valueOf(roleStr.toUpperCase());
            }
        } catch (SQLException | IllegalArgumentException e) {
            log.error("error get role", e);
            throw new DaoException("error get role", e);
        } finally {
            try {
                if (resultSet != null & preparedStatement != null) {
                    resultSet.close();
                    preparedStatement.close();
                }
                connection.close();
            } catch (SQLException e) {
                throw new DaoException("error close connection", e);
            }
        }
        return null;
    }

}
