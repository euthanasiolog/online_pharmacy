package com.epam.pharmacy.dao;

import com.epam.pharmacy.dao.impl.ClientDaoImpl;
import com.epam.pharmacy.dao.impl.PharmacistDaoImpl;
import com.epam.pharmacy.entity.user.User;
import com.epam.pharmacy.util.constant.Role;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface UserDao extends AbstractDao {
    static User signIn(String login, String password, Role role) throws SQLException {
                    switch (role) {
                        case CLIENT:
                            return new ClientDaoImpl().signIn(login, password);
                        case DOCTOR:
                            return new ClientDaoImpl().signIn(login, password);
                        case PHARMACIST:
                            return new PharmacistDaoImpl().signIn(login, password);
                        default: return null;
                    }
    }
    User signIn(String login, String password) throws SQLException;

    default void setUserAttributes(User user, ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            user.setEMail(resultSet.getString("email"));
            user.setUserName(resultSet.getString("username"));
        }
    }
}
