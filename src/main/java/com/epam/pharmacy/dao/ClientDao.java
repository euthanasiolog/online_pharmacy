package com.epam.pharmacy.dao;


import com.epam.pharmacy.entity.user.Client;

import java.sql.SQLException;

public interface ClientDao extends UserDao {
    @Override
    Client signIn(String login, String password) throws SQLException;
}
