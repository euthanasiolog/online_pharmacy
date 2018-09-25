package com.epam.online_pharmacy.util.listener;

import com.epam.online_pharmacy.util.constant.ProjectConstant;
import com.epam.online_pharmacy.util.constant.Role;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnSessionCreateListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(ProjectConstant.ROLE, Role.GUEST);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
