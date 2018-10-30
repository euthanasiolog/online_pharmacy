package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.service.UserService;
import com.epam.pharmacy.service.UserServiceFactory;
import com.epam.pharmacy.util.constant.Role;


public class UserServiceFactoryImpl implements UserServiceFactory {

    private static UserServiceFactoryImpl instance = new UserServiceFactoryImpl();

    @Override
    public UserService getUserService(Role role) {
        switch (role){
            case CLIENT:
                return new ClientServiceImpl();
            case DOCTOR:
                return new DoctorServiceImpl();
            case PHARMACIST:
                return new PharmacistServiceImpl();
            case ADMIN:
                return new AdminServiceImpl();
            default:
                return null;
        }
    }

    private UserServiceFactoryImpl (){}

    public static UserServiceFactoryImpl getInstance (){
        if (instance==null){
            instance = new UserServiceFactoryImpl();
        }
        return instance;
    }

}
