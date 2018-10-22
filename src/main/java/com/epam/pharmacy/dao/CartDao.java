package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.Cart;

public interface CartDao extends AbstractDao <Cart> {

    boolean putDrugInCart (int idClient, int idDrug, int number);

    boolean deleteOrderFromCart (int id);
}
