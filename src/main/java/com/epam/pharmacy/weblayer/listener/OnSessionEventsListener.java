package com.epam.pharmacy.weblayer.listener;

import com.epam.pharmacy.dao.CartDao;
import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.impl.CartDaoImpl;
import com.epam.pharmacy.model.Cart;
import com.epam.pharmacy.service.CartService;
import com.epam.pharmacy.service.impl.CartServiceImpl;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;
import lombok.extern.log4j.Log4j2;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;

@Log4j2
@WebListener
public class OnSessionEventsListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(ProjectConstant.ROLE, Role.GUEST);
        se.getSession().setAttribute(ProjectConstant.LOCALE, new Locale(ProjectConstant.BY_LOCALE_LANG, ProjectConstant.BY_LOCALE_COUNTRY));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        CartDao cartDao = new CartDaoImpl();
        try {
            cartDao.create((Cart) se.getSession().getAttribute(ProjectConstant.CART), null);
        } catch (DaoException e) {
            log.error("error save cart");
        }
    }
}
