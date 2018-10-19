package com.epam.pharmacy.weblayer.listener;

import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;

@WebListener
public class OnSessionCreateListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute(ProjectConstant.ROLE, Role.GUEST);
        se.getSession().setAttribute(ProjectConstant.LOCALE, new Locale(ProjectConstant.BY_LOCALE_LANG, ProjectConstant.BY_LOCALE_COUNTRY));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
