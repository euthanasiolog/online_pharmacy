package com.epam.pharmacy.dao;

import com.epam.pharmacy.entity.user.User;

import java.sql.SQLException;

public interface PharmacistDao extends UserDao {
    @Override
    User signIn(String login, String password) throws SQLException;
}
