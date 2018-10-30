package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.PharmacistDao;
import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.util.constant.Role;
import lombok.extern.log4j.Log4j2;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class PharmacistDaoImpl implements PharmacistDao {
    @Override
    public boolean create(User user, String password) throws DaoException {
        List<Object> userParams = createUserAttributes(user, password, Role.PHARMACIST);
        return executeQueryUpdate(SQLQueries.CREATE_USER_QUERY, userParams);
    }

    @Override
    public User findById(int id) throws DaoException {
        List<Object> param = new ArrayList<>();
        param.add(id);
        ResultSetWrapper resultSetUser = executeQueryResult(SQLQueries.FIND_USER_BY_ID, param);
        if (!resultSetUser.isEmpty()
                && Role.PHARMACIST.toString().equalsIgnoreCase((String) resultSetUser.getResult().get(0).get("type"))){
            User user = new User();
            readUserAttributes(user, resultSetUser.getResult().get(0));
            return user;
        }
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User entity) throws DaoException {
        return false;
    }

    @Override
    public User signIn(String login, String password) throws DaoException {
        ResultSetWrapper resultSetUser = signInUser(login, password, SQLQueries.USER_SIGN_IN);

        if (!resultSetUser.isEmpty()) {
            User user = new User();
            readUserAttributes(user, resultSetUser.getResult().get(0));
            return user;
        }
        return null;
    }

    @Override
    public List<User> getPharmacistQueries() throws DaoException {
        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.GET_PHARMACISTS_QUERIES);

        return getUsersList(resultSet);
    }

    @Override
    public List<User> getPharmacists() throws DaoException {
        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.GET_PHARMACISTS);

        return getUsersList(resultSet);
    }

    private List<User> getUsersList(ResultSetWrapper resultSet) {
        List<User> pharmacists = new ArrayList<>();
        if (!resultSet.isEmpty()) {
            for (Map<String, Object> res : resultSet.getResult()) {
                User user = new User();
                readUserAttributes(user, res);
                pharmacists.add(user);
            }
        }
        return pharmacists;
    }
}
