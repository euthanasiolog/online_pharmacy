package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.Cart;
import com.epam.pharmacy.model.item.Order;

public interface CartDao extends AbstractDao <Cart> {

    boolean putDrugInCart (int idClient, int idDrug, int number, int payment) throws DaoException;

    boolean deleteOrderFromCart (int id) throws DaoException;

    boolean doPayment (int...id) throws DaoException;

    boolean updateOrder (int number, int payment, int archive, int idOrder) throws DaoException;

    Order findOrderById (int id) throws DaoException;

}
