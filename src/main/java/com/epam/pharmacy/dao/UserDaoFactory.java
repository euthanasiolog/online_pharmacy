package com.epam.pharmacy.dao;

import com.epam.pharmacy.util.constant.Role;

public interface UserDaoFactory {

    UserDao createUserDAO (Role role);

    Role checkUserRole (String login, String password) throws DaoException;

}
