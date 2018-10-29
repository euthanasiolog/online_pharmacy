package com.epam.pharmacy.service;

import com.epam.pharmacy.exception.ApplicationException;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;

public interface CartService extends AbstractService {

    CommandResult putDrugInCart (RequestContent requestContent) throws ApplicationException;

    CommandResult putOrderInDb (RequestContent requestContent) throws ApplicationException;

    CommandResult payment (RequestContent requestContent) throws ApplicationException;

    CommandResult doPayment (RequestContent requestContent) throws ApplicationException;
}
