package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.Cart;

public interface CartDao extends AbstractDao <Cart> {

    boolean putDrugInCart (int idClient, int idDrug, int number, int payment) throws DaoException;

    boolean deleteOrderFromCart (int id) throws DaoException;

    boolean payment (int id) throws DaoException;
}
