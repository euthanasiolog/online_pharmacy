package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.dao.CartDao;
import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.impl.CartDaoImpl;
import com.epam.pharmacy.exception.ApplicationException;
import com.epam.pharmacy.model.Cart;
import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.model.item.Order;
import com.epam.pharmacy.model.user.Client;
import com.epam.pharmacy.service.CartService;
import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.weblayer.command.ResponseType;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CartServiceImpl implements CartService {

    @Override
    public CommandResult create(RequestContent requestContent) throws ApplicationException {
        Cart cart = (Cart) requestContent.getSessionAtribute("cart");

        CartDao cartDao = new CartDaoImpl();
        try {
            cartDao.create(cart, null);
        } catch (DaoException e) {
            log.error("error save cart", e);
            throw new ApplicationException("error save cart", e);
        }
        return null;
    }

    @Override
    public CommandResult findById(RequestContent requestContent) throws ApplicationException {
        int id = ((Client) requestContent.getSessionAtribute(ProjectConstant.USER)).getId();

        CartDao cartDao = new CartDaoImpl();
        try {
            Cart cart = cartDao.findById(id);
            requestContent.insertSessionAtribute(ProjectConstant.CART, cart);

            return new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE));
        } catch (DaoException e) {
            throw new ApplicationException("error find cart", e);
        }
    }

    @Override
    public CommandResult update(RequestContent requestContent) {
        return null;
    }

    @Override
    public CommandResult delete(RequestContent requestContent) {
        Cart cart = (Cart) requestContent.getSessionAtribute(ProjectConstant.CART);

        Order order = new Order();
        order.setDrug((Drug) requestContent.getRequestAttribute(ProjectConstant.DRUG));
        order.setNumber(Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.NUMBER)));

        cart.deleteOrder(order);

        requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult putDrugInCart(RequestContent requestContent) {
        Cart cart = (Cart) requestContent.getSessionAtribute(ProjectConstant.CART);

        Order order = new Order();
        order.setDrug((Drug) requestContent.getRequestAttribute(ProjectConstant.DRUG));
        order.setNumber(Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.NUMBER)));

        cart.addOrder(order);

        requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult payment(RequestContent requestContent) {
        Order order = (Order) requestContent.getSessionAtribute(ProjectConstant.ORDER);
        order.setPayment(true);

        requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

}
