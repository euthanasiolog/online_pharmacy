package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.user.Doctor;
import com.epam.pharmacy.exception.ApplicationException;

import java.util.List;

public interface DoctorDao extends UserDao<Doctor> {
    @Override
    Doctor signIn(String login, String password) throws DaoException;

    List<Doctor> getDoctors () throws DaoException;

    List<Doctor> getDoctorsQueries () throws DaoException;

}
