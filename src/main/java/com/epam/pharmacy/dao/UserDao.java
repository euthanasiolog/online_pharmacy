package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.user.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public interface UserDao<T extends User> extends AbstractDao<T> {

    T signIn(String login, String password) throws DaoException;

    default ResultSet signInUser (String login, String password, String query) throws DaoException {
        List<Object> userParams = new ArrayList<>();
        userParams.add(login);
        userParams.add(password);

        return executeQueryResult(query, userParams);
    }

    default void readUserAttributes(User user, ResultSet resultSet) throws DaoException {
        try {
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setEMail(resultSet.getString("email"));
                user.setNickName(resultSet.getString("nickname"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                if (resultSet.getString("patronymic")!=null){
                    user.setPatronymic(resultSet.getString("patronymic"));
                }
                user.setDateOfBirth(resultSet.getDate("dateofbirth"));
            }
        } catch (SQLException e) {
            throw new DaoException("error read user attributes", e);
        }

    }

    default List<Object> createUserAttributes(User user, String password) {

        List<Object> params = new ArrayList<>();

        params.add(user.getNickName());
        params.add(password);
        params.add(user.getEMail());
        params.add(user.getFirstName());
        params.add(user.getLastName());
        if (user.getPatronymic()!=null){
            params.add(user.getPatronymic());
        }
        params.add(user.getDateOfBirth());

        return params;
    }
}
