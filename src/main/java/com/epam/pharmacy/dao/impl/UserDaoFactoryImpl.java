package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.UserDaoFactory;
import com.epam.pharmacy.model.user.Client;
import com.epam.pharmacy.model.user.Doctor;
import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.util.constant.Role;

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
    public User signIn(String login, String password, Role role) throws DaoException {
        switch (role){
            case CLIENT:
                return new ClientDaoImpl().signIn(login, password);
            case DOCTOR:
                return new DoctorDaoImpl().signIn(login, password);
            case PHARMACIST:
                return new PharmacistDaoImpl().signIn(login, password);
            default:
                return null;
        }
    }

    @Override
    public boolean create(User user, String password, Role role) throws DaoException {
        switch (role){
            case CLIENT:
                return new ClientDaoImpl().create((Client) user, password);
            case DOCTOR:
                return new DoctorDaoImpl().create((Doctor) user, password);
            case PHARMACIST:
                return new PharmacistDaoImpl().create(user, password);
            default:
                return false;
        }
    }

    @Override
    public User findById(int id, Role role) throws DaoException {
        switch (role) {
            case CLIENT:
                return new ClientDaoImpl().findById(id);
            case DOCTOR:
                return new DoctorDaoImpl().findById(id);
            case PHARMACIST:
                return new PharmacistDaoImpl().findById(id);
            default:
                return null;
        }
    }

    @Override
    public boolean update(User user, Role role) throws DaoException {
        switch (role) {
            case CLIENT:
                return new ClientDaoImpl().update((Client) user);
            case DOCTOR:
                return new DoctorDaoImpl().update((Doctor) user);
            case PHARMACIST:
                return new PharmacistDaoImpl().update(user);
            default:
                return false;
        }
    }

}
