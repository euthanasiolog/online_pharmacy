package com.epam.pharmacy.dao;

import com.epam.pharmacy.entity.user.Doctor;

import java.sql.SQLException;

public interface DoctorDao extends UserDao {
    @Override
    Doctor signIn(String login, String password) throws SQLException;
}
