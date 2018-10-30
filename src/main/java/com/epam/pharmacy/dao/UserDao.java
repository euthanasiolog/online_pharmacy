package com.epam.pharmacy.dao;

import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.util.constant.Role;
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
    default boolean delete(User user) throws DaoException {
        int id = user.getId();
        List<Object> param = putParameters(id);

        return executeQuery("UPDATE user SET `archive`='1' WHERE id=?", param);
    }

    default ResultSetWrapper signInUser (String login, String password, String query) throws DaoException {
        List<Object> userParams = putParameters(login, password);

        return executeQueryResult(query, userParams);
    }

    default void readUserAttributes(User user, Map<String, Object> resultSet) {
            user.setId((Integer) resultSet.get("id"));
            user.setEmail((String) resultSet.get("email"));
            user.setNickName((String) resultSet.get("nickname"));
            user.setFirstName((String) resultSet.get("firstname"));
            user.setLastName((String) resultSet.get("lastname"));
            if (resultSet.get("patronymic")!=null){
                user.setPatronymic((String) resultSet.get("patronymic"));
            }
            user.setDateOfBirth((Date) resultSet.get("birthdate"));
    }

    default List<Object> createUserAttributes(User user, String password, Role role) {

        return putParameters(user.getNickName(), password, user.getEmail(), user.getFirstName(),
                user.getLastName(), user.getPatronymic(), role.toString().toLowerCase(), user.getDateOfBirth());
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
