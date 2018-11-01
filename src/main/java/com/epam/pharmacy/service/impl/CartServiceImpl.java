package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.dao.CartDao;
import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.DrugDao;
import com.epam.pharmacy.dao.impl.CartDaoImpl;
import com.epam.pharmacy.dao.impl.DrugDaoImpl;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Log4j2
public class CartServiceImpl implements CartService {

    @Override
    public CommandResult create(RequestContent requestContent) throws ApplicationException {
        Cart cart = (Cart) requestContent.getSessionAttribute("cart");

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
        int id = ((Client) requestContent.getSessionAttribute(ProjectConstant.USER)).getId();

        CartDao cartDao = new CartDaoImpl();
        try {
            Cart cart = cartDao.findById(id);
            requestContent.insertSessionAttribute(ProjectConstant.CART, cart);

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
        Cart cart = (Cart) requestContent.getSessionAttribute(ProjectConstant.CART);
        int orderId = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ORDER));
        Order order;
        if (orderId==0) {
            order = findNotPaidOrderByDrugId(cart, orderId);
            cart.deleteOrder(order);
        } else {
            order = findOrderById(cart, orderId);
            cart.deleteOrder(order);
        }

        requestContent.insertSessionAttribute(ProjectConstant.CART, cart);
        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult putDrugInCart(RequestContent requestContent) throws ApplicationException {
        Cart cart = (Cart) requestContent.getSessionAttribute(ProjectConstant.CART);

        Order order = new Order();
        if (requestContent.getRequestParameter(ProjectConstant.NUMBER) != null && !requestContent.getRequestParameter(ProjectConstant.NUMBER).equals("")) {
            int drugId = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));
            int number = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.NUMBER));

            DrugDao drugDao = new DrugDaoImpl();
            Drug drug;
            try {
                drug = (drugDao.findById(drugId));
            } catch (DaoException e) {
                log.error("error find drug", e);
                throw new ApplicationException("error find drug", e);
            }

            if (drug != null) {
                order.setDrug(drug);
                order.setNumber(number);
                cart.addOrder(order);
                requestContent.insertSessionAttribute(ProjectConstant.CART, cart);
            }
        }
        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult putOrderInDb(RequestContent requestContent) throws ApplicationException {
        Cart cart = (Cart) requestContent.getSessionAttribute(ProjectConstant.CART);

            int clientId = cart.getClientId();
            int drugId = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.DRUG));
            int number = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.NUMBER));
            int payment = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.PAYMENT));

            CartDao cartDao = new CartDaoImpl();
            try {
                cartDao.putDrugInCart(clientId, drugId, number, payment);
            } catch (DaoException e) {
                log.error("error put order in db", e);
                throw new ApplicationException("error put order in db", e);
            }
        return new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE));
    }

    @Override
    public CommandResult payment(RequestContent requestContent) throws ApplicationException {
        Cart cart = (Cart) requestContent.getSessionAttribute(ProjectConstant.CART);
        int id = cart.getClientId();

        CartDao cartDao = new CartDaoImpl();
        try {
            cartDao.create(cart, null);
            cart = (cartDao.findById(id));
        } catch (DaoException e) {
            log.error("error update cart", e);
            throw new ApplicationException("error update cart", e);
        }
        requestContent.insertSessionAttribute(ProjectConstant.CART, cart);
        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PAYMENT);
    }

    @Override
    public CommandResult doPayment(RequestContent requestContent) throws ApplicationException {
        Cart cart = (Cart) requestContent.getSessionAttribute(ProjectConstant.CART);
        String[] idStr = requestContent.getRequestParameters(ProjectConstant.ID);

        if (idStr.length != 0) {
            int[] id = Stream.of(idStr).mapToInt(Integer::parseInt).toArray();
            int clientId = cart.getClientId();

            CartDao cartDao = new CartDaoImpl();
            try {
                boolean b = cartDao.doPayment(id);
                cart = cartDao.findById(clientId);
            } catch (DaoException e) {
                log.error("error do payment", e);
                throw new ApplicationException("error do payment", e);
            }
        }
        requestContent.insertSessionAttribute(ProjectConstant.CART, cart);
        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, PagePath.PAYMENT);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    private Order findOrderById (Cart cart, int id) {
        List<Order> orders = cart.getOrders();
        return orders.stream().filter(p -> p.getId()==id).
                findFirst().orElse(null);
    }

    private Order findNotPaidOrderByDrugId (Cart cart, int id) {
        List<Order> orders = cart.getOrders();
        return orders.stream().filter(p -> !p.isPayment()).
                filter(p -> p.getDrug().getId()==id).
                findFirst().orElse(null);
    }

}
