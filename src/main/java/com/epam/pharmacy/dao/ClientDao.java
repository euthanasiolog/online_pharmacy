package com.epam.pharmacy.dao;


import com.epam.pharmacy.model.user.Client;
import com.epam.pharmacy.exception.ApplicationException;

public interface ClientDao extends UserDao<Client> {
    @Override
    Client signIn(String login, String password) throws DaoException;
}
