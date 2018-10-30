package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.ClientDao;
import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.user.Client;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;
import lombok.extern.log4j.Log4j2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class ClientDaoImpl implements ClientDao {
    @Override
    public boolean create(Client client, String password) throws DaoException {

        boolean b;
        Connection connection = startTransaction();
        try (PreparedStatement preparedStatementUser = connection.prepareStatement(SQLQueries.CREATE_USER);
        PreparedStatement preparedStatementClient =  connection.prepareStatement(SQLQueries.CREATE_CLIENT)) {

            List<Object> userParams = createUserAttributes(client, password, Role.CLIENT);
            setQueryParams(userParams, preparedStatementUser);
            preparedStatementUser.executeUpdate();

            List<Object> clientParams = putParameters(client.getDiscount());
            setQueryParams(clientParams, preparedStatementClient);
            preparedStatementClient.executeUpdate();
            b = commitTransaction(connection);

        } catch (SQLException e) {
            throw new DaoException("", e);
        }
        return b;
    }

    @Override
    public Client signIn(String login, String password) throws DaoException {

        ResultSetWrapper resultSetUser = signInUser(login, password, SQLQueries.USER_SIGN_IN);

        if (!resultSetUser.isEmpty()) {
            Map<String, Object> result = resultSetUser.getResult().get(0);
            List<Object> clientParams = new ArrayList<>();

            clientParams.add(result.get("id"));
            ResultSetWrapper resultSetClient = executeQueryResult(SQLQueries.CLIENT_SIGN_IN_SQL, clientParams);

            return getClient(resultSetUser, resultSetClient);
        }
        return null;
    }



    @Override
    public Client findById(int id) throws DaoException {
        List<Object> param = new ArrayList<>();
        param.add(id);
        ResultSetWrapper resultSetUser = executeQueryResult(SQLQueries.FIND_USER_BY_ID, param);
        if (!resultSetUser.isEmpty()){
            ResultSetWrapper resultSetClient = executeQueryResult(SQLQueries.CLIENT_SIGN_IN_SQL, param);
            return getClient(resultSetUser, resultSetClient);
        }
        return null;
    }

    @Override
    public boolean update(Client client) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Client entity) throws DaoException {
        return false;
    }

    private Client getClient(ResultSetWrapper resultSetUser, ResultSetWrapper resultSetClient) {
        if (!resultSetClient.isEmpty()) {
            Client client = new Client();
            readUserAttributes(client, resultSetUser.getResult().get(0));
            readClientAttributes(client, resultSetClient);
            return client;
        }
        return null;
    }

    private void readClientAttributes (Client client, ResultSetWrapper resultSet) {
            Map<String, Object> result = resultSet.getResult().get(0);
            client.setDiscount((int)result.get("discount"));
    }

}
