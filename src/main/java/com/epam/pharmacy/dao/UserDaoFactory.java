package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.util.constant.Role;

public interface UserDaoFactory {

    User signIn(String login, String password, Role role) throws DaoException;

    boolean create(User user, String password, Role role) throws DaoException;

    User findById(int id, Role role) throws DaoException;

    boolean update(User entity, Role role) throws DaoException;

}
