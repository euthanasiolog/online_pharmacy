package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.CartDao;
import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.DrugDao;
import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.Cart;
import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.model.item.Order;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.weblayer.command.RequestContent;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

@Log4j2
public class CartDaoImpl implements CartDao {

    @Override
    public boolean putDrugInCart(int idClient, int idDrug, int number, int payment) throws DaoException {
        List<Object> params = putParameters(idClient, idDrug, number, payment);

        return executeQuery(SQLQueries.PUT_DRUG_IN_CART, params);
    }

    @Override
    public boolean deleteOrderFromCart(int id) throws DaoException {
        List<Object> params = putParameters(id);

        return executeQueryUpdate(SQLQueries.DELETE_DRUG_FROM_CART, params);
    }

    @Override
    public boolean doPayment(int...idOrder) throws DaoException {
        Connection connection = startTransaction();
        for (int id : idOrder) {
            Order order = findOrderById(id);
            int amount = order.getDrug().getAmount();
            if (amount > order.getNumber()) {
                List<Object> params = putParameters(id);
                executeQueryUpdate(SQLQueries.DO_PAYMENT, params);

                DrugDao drugDao = new DrugDaoImpl();
                Drug drug = drugDao.findById(order.getDrug().getId());
                drug.setAmount(amount - order.getNumber());
                drugDao.update(drug);
            } else {
                log.debug("no such drugs in pharmacy");
             //TODO:  no such drugs in pharmacy
            }
        }
        return commitTransaction(connection);
    }

    @Override
    public boolean updateOrder(int number, int payment, int archive, int idOrder) throws DaoException {
        List<Object> params = putParameters(number, payment, archive, idOrder);

        return executeQueryUpdate(SQLQueries.UPDATE_DRUG_IN_CART, params);
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
            int archive = 0;
            if (order.isArchive()) {
                archive = 1;
            }

            int id = order.getId();
            if (id == 0) {
                List<Object> params = putParameters(cart.getClientId(), order.getDrug().getId(), order.getNumber(),
                        payment);
                executeQuery(SQLQueries.PUT_DRUG_IN_CART, params, connection);
            } else {
                List<Object> params = putParameters(order.getNumber(),
                        payment, archive, id);
                executeQueryUpdate(SQLQueries.UPDATE_DRUG_IN_CART, params, connection);
            }
        }
        return commitTransaction(connection);
    }

    @Override
    public Order findOrderById(int id) throws DaoException {
        List<Object> params = putParameters(id);
        Order order = null;
        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.GET_ORDER_BY_ID, params);


        if (!resultSet.isEmpty()) {
            Map<String, Object> res = resultSet.getResult().get(0);
            int clientId = (int) res.get(ProjectConstant.ID_CLIENT);
            int drugId = (int) res.get(ProjectConstant.ID_DRUG);
            int number = (int) res.get(ProjectConstant.NUMBER);
            boolean payment = (boolean) res.get(ProjectConstant.PAYMENT);

            DrugDao drugDao = new DrugDaoImpl();

            order = new Order();
            order.setDrug(drugDao.findById(drugId));
            order.setNumber(number);
            order.setPayment(payment);
        }

        return order;
    }

    @Override
    public Cart findById(int id) throws DaoException {
        Cart cart = new Cart(id);

        List<Object> params = putParameters(id);
        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.GET_CLIENT_CART, params);

        if (!resultSet.isEmpty()) {
            for (Map<String, Object> res : resultSet.getResult()) {
                int idDrug = (int) res.get("id_drug");

                DrugDao drugDao = new DrugDaoImpl();
                Drug drug = drugDao.findById(idDrug);

                if (drug != null) {
                    int idOrder = (int) res.get("id");
                    int number = (int) res.get("number");
                    boolean payment = (boolean)res.get("payment");

                    Order order = new Order();
                    order.setId(idOrder);
                    order.setDrug(drug);
                    order.setNumber(number);
                    order.setPayment(payment);
                    cart.addOrder(order);
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
