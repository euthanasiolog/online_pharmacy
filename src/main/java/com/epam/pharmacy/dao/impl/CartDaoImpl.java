package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.CartDao;
import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.DrugDao;
import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.Cart;
import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.model.item.Order;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class CartDaoImpl implements CartDao {

    @Override
    public boolean putDrugInCart(int idClient, int idDrug, int number, int payment) throws DaoException {
        List<Object> params = putParameters(idClient, idDrug, number, payment);

        return executeQuery(SQLQueries.PUT_DRUG_IN_CART, params);
    }

    @Override
    public boolean deleteOrderFromCart(int id) throws DaoException {
        List<Object> params = putParameters(id);

        return executeQuery(SQLQueries.DELETE_DRUG_FROM_CART, params);
    }

    @Override
    public boolean payment(int id) throws DaoException {
        List<Object> params = putParameters(id);

        return executeQuery(SQLQueries.DO_PAYMENT, params);
    }


    @Override
    public boolean create(Cart cart, String password) throws DaoException {
        List<Order> orders = cart.getOrders();

        Connection connection = startTransaction();

        for (Order order : orders) {
            int payment = 0;
            if (order.isPayment()) {
                payment = 1;
            }
            List<Object> params = putParameters(cart.getClientId(), order.getDrug().getId(), order.getNumber(),
                    payment);
            executeQueryTransaction(SQLQueries.PUT_DRUG_IN_CART, params, connection);
        }

        return commitTransaction(connection);
    }

    @Override
    public Cart findById(int id) throws DaoException {
        List<Object> params = putParameters(id);

        Cart cart = new Cart(id);

        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.GET_CLIENT_CART, params);

        if (!resultSet.isEmpty()) {
            for (Map<String, Object> res:resultSet.getResult()) {
                int idDrug = (int) res.get("id_drug");
                DrugDao drugDao = new DrugDaoImpl();
                Drug drug = drugDao.findById(idDrug);
                if (drug != null) {
                    int number = (int) res.get("number");
                    int payment = (int) res.get("payment");

                    Order order = new Order();
                    order.setDrug(drug);
                    order.setNumber(number);
                    if (payment == 1) {
                        order.setPayment(true);
                    }
                }
            }
        }
        return cart;
    }

    @Override
    public boolean update(Cart cart) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Cart cart) throws DaoException {
        return false;
    }
}
