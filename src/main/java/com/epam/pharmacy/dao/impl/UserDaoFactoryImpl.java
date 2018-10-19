package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.UserDao;
import com.epam.pharmacy.dao.UserDaoFactory;
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
    public UserDao createUserDAO(Role role) {
        switch (role){
            case CLIENT:
                return new ClientDaoImpl();
            case DOCTOR:
                return new DoctorDaoImpl();
            case PHARMACIST:
                return new PharmacistDaoImpl();
            default:
               return null;
        }
    }

}
