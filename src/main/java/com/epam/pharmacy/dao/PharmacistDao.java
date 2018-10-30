package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.exception.ApplicationException;

import java.util.List;

public interface PharmacistDao extends UserDao<User> {
    @Override
    User signIn(String login, String password) throws DaoException;

    List<User> getPharmacistQueries () throws DaoException;

    List<User> getPharmacists () throws DaoException;
}
