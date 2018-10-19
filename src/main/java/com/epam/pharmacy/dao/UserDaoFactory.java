package com.epam.pharmacy.dao;

import com.epam.pharmacy.util.constant.Role;

@FunctionalInterface
public interface UserDaoFactory {

    UserDao createUserDAO (Role role);

}
