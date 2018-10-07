package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.user.Doctor;
import com.epam.pharmacy.exception.ApplicationException;

public interface DoctorDao extends UserDao<Doctor> {
    @Override
    Doctor signIn(String login, String password) throws ApplicationException;
}
