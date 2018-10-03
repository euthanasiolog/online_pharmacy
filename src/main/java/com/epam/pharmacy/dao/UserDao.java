package com.epam.pharmacy.dao;

import com.epam.pharmacy.dao.impl.ClientDaoImpl;
import com.epam.pharmacy.dao.impl.PharmacistDaoImpl;
import com.epam.pharmacy.entity.user.User;
import com.epam.pharmacy.util.constant.Role;

import java.sql.PreparedStatement;
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

    default void readUserAttributes(User user, ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            user.setEMail(resultSet.getString("email"));
            user.setUserName(resultSet.getString("username"));
            user.setFirstName(resultSet.getString("firstname"));
            user.setLastName(resultSet.getString("lastname"));
            if (resultSet.getString("patronymic")!=null){
                user.setPatronymic(resultSet.getString("patronymic"));
            }
            user.setDateOfBirth(resultSet.getDate("dateofbirth"));
        }
    }

    default void createUserAttributes(User user, PreparedStatement preparedStatement, String password) throws SQLException {
        preparedStatement.setString(1,user.getUserName());
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, user.getEMail());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getLastName());
        if (user.getPatronymic()!=null){
            preparedStatement.setString(6, user.getPatronymic());
        }
        preparedStatement.setDate(8, user.getDateOfBirth());
    }
}
