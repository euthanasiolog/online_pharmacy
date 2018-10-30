package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.AdminDao;
import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.Entity;
import com.epam.pharmacy.model.user.User;

import java.util.List;

public class AdminDaoImpl implements AdminDao {

    @Override
    public User signIn(String login, String password) throws DaoException {
        ResultSetWrapper resultSetUser = signInUser(login, password, SQLQueries.USER_SIGN_IN);

        if (!resultSetUser.isEmpty()) {
            User user = new User();
            readUserAttributes(user, resultSetUser.getResult().get(0));
            return user;
        }
        return null;
    }

    @Override
    public boolean confirmUserRegistrationQuery(int userId) throws DaoException {
        List<Object> params = putParameters(userId);

        return executeQueryUpdate(SQLQueries.CONFIRM_USER_QUERY, params);
    }

    @Override
    public boolean deleteUser(int userId) throws DaoException {
        List<Object> params = putParameters(userId);

        return executeQueryUpdate(SQLQueries.DELETE_USER, params);
    }

    @Override
    public boolean dropArchiveDrugTable() throws DaoException {
        return false;
    }

    @Override
    public boolean dropArchiveUserTable() throws DaoException {
        return false;
    }

    @Override
    public boolean dropArchiveRecipeTable() throws DaoException {
        return false;
    }

    @Override
    public boolean dropArchiveOrderTable() throws DaoException {
        return false;
    }




    @Override
    public boolean create(Entity entity, String password) throws DaoException {
        return false;
    }

    @Override
    public Entity findById(int id) throws DaoException {
        return null;
    }

    @Override
    public boolean update(Entity entity) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Entity entity) throws DaoException {
        return false;
    }

}
