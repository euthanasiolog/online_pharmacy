package com.epam.pharmacy.util.listener;

import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@WebListener
public class OnSessionCreateListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(ProjectConstant.ROLE, Role.GUEST);
        se.getSession().setAttribute("locale", ProjectConstant.BY_LOCALE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
