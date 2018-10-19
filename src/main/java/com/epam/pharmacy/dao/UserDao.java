package com.epam.pharmacy.dao;

import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.user.User;
import org.apache.logging.log4j.core.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface UserDao<T extends User> extends AbstractDao<T> {

    T signIn(String login, String password) throws DaoException;

    @Override
    default boolean delete(T user) throws DaoException {
        int id = user.getId();
        List<Object> param = new ArrayList<>();
        param.add(id);
        return executeQuery("UPDATE user SET `archive`='1' WHERE id=?", param);
    }

    default ResultSetWrapper signInUser (String login, String password, String query) throws DaoException {
        List<Object> userParams = new ArrayList<>();
        userParams.add(login);
        userParams.add(password);

        return executeQueryResult(query, userParams);
    }

    default void readUserAttributes(User user, ResultSetWrapper resultSetWrapper) {
        List<Map<String, Object>> result = resultSetWrapper.getResult();
        if (!result.isEmpty()) {
            Map<String, Object> resultSet = result.get(0);

            user.setId((Integer) resultSet.get("id"));
            user.setEMail((String) resultSet.get("email"));
            user.setNickName((String) resultSet.get("nickname"));
            user.setFirstName((String) resultSet.get("firstname"));
            user.setLastName((String) resultSet.get("lastname"));
            if (resultSet.get("patronymic")!=null){
                user.setPatronymic((String) resultSet.get("patronymic"));
            }
            user.setDateOfBirth((Date) resultSet.get("dateofbirth"));
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

    default boolean isNickNameNotExist (String nickName) throws DaoException {
        List<Object> userParams = new ArrayList<>();
        userParams.add(nickName);

        ResultSetWrapper resultSet = executeQueryResult("select u.nickname from user u where u.nickname=?", userParams);
        List<Map<String, Object>> result = resultSet.getResult();

        return result.isEmpty();
    }

    default boolean isEmailNotExist (String email) throws DaoException {

        List<Object> userParams = new ArrayList<>();
        userParams.add(email);

        ResultSetWrapper resultSet = executeQueryResult("select u.id, u.nickname, u.email\n" +
                "from user u where u.email=?", userParams);

        List<Map<String, Object>> result = resultSet.getResult();

        return result.isEmpty();
    }
}
