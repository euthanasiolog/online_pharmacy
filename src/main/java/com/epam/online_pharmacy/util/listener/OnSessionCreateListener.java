package com.epam.online_pharmacy.util.listener;

import com.epam.online_pharmacy.util.constant.PagePass;
import com.epam.online_pharmacy.util.constant.ProjectConstant;
import com.epam.online_pharmacy.util.constant.Role;
import com.epam.online_pharmacy.util.localization.LocaleType;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnSessionCreateListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(ProjectConstant.ROLE, Role.GUEST);
        se.getSession().setAttribute("pageForward", new PageForward(PagePass.START_PAGE));
        se.getSession().setAttribute("locale", LocaleType.en_US);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
