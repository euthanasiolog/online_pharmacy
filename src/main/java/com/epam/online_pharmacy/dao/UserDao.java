package com.epam.online_pharmacy.dao;

import com.epam.online_pharmacy.dao.AbstractDao;
import com.epam.online_pharmacy.entity.user.User;

public interface UserDao extends AbstractDao {
    User signIn(String login, String password);
}
