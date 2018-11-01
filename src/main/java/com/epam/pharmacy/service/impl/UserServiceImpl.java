package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.UserDaoFactory;
import com.epam.pharmacy.dao.impl.UserDaoFactoryImpl;
import com.epam.pharmacy.service.UserServiceFactory;
import com.epam.pharmacy.util.validator.RequestValidator;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.weblayer.command.ResponseType;
import com.epam.pharmacy.dao.UserDao;
import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.service.UserService;
import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;
import com.epam.pharmacy.exception.ApplicationException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserServiceImpl implements UserService {

    @Override
    public CommandResult signIn(RequestContent requestContent) throws ApplicationException {

        RequestValidator validator = new RequestValidator();

        if (validator.isPasswordAndLoginValid(requestContent)) {

            String password = hashPassword(requestContent.getRequestParameter(ProjectConstant.PASSWORD), log);
            String login = requestContent.getRequestParameter(ProjectConstant.LOGIN);

            UserDaoFactory userDaoFactory = UserDaoFactoryImpl.getInstance();
            Role role;
            try {
                role = userDaoFactory.checkUserRole(login, password);
            } catch (DaoException e) {
                log.error("error check role", e);
                throw new ApplicationException("error check role", e);
            }

            if (role != null) {
                User user;
                UserDao userDao = userDaoFactory.createUserDAO(role);

                try {
                    user = userDao.signIn(login, password);
                } catch (DaoException e) {
                    log.error("error create user", e);
                    throw new ApplicationException("error create user", e);
                }
                if (user != null) {
                    insertUserSessionAttributes(requestContent, user, role);
                    return endSignIn(user, requestContent, role);
                }
            } else {
                requestContent.insertAttribute("loginError", "Login or password incorrect");
            }
        }
        return new CommandResult(ResponseType.FORWARD, PagePath.START_PAGE);
    }

    @Override
    public CommandResult endSignIn(User user, RequestContent requestContent, Role role) throws ApplicationException {
        UserServiceFactory userServiceFactory = UserServiceFactoryImpl.getInstance();
        UserService userService = userServiceFactory.getUserService(role);

        return userService.endSignIn(user, requestContent, role);
    }

    @Override
    public CommandResult registration(RequestContent requestContent) {
        try {
            Role role = Role.valueOf(requestContent.getRequestParameter(ProjectConstant.ROLE).toUpperCase());
            switch (role){
                case CLIENT:
                    requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, PagePath.REGISTRATION_CLIENT_PAGE);
                    return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
                case DOCTOR:
                    requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, PagePath.REGISTRATION_DOCTOR_PAGE);
                    return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
                case PHARMACIST:
                    requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, PagePath.REGISTRATION_PHARMACIST_PAGE);
                    return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
                default:
                    return new CommandResult(ResponseType.FORWARD, PagePath.START_PAGE);
            }
        } catch (IllegalArgumentException e){
            log.error("Error to define role from enum");
            return new CommandResult(ResponseType.FORWARD, PagePath.START_PAGE);
        }
    }

    @Override
    public CommandResult goToCabinet(RequestContent requestContent) {
        if (requestContent.getSessionAttribute(ProjectConstant.ROLE) != null) {
            Role role = null;
            try {
                role = (Role) requestContent.getSessionAttribute(ProjectConstant.ROLE);
            } catch (IllegalArgumentException e) {
                log.error("no enum for " + requestContent.getRequestParameter(ProjectConstant.ROLE), e);
            }

            if (Role.GUEST.equals(role)) {
                return new CommandResult(ResponseType.FORWARD, PagePath.START_PAGE);
            }

            UserServiceFactory factory = UserServiceFactoryImpl.getInstance();

            return factory.getUserService(role).goToCabinet(requestContent);
        }
        return new CommandResult(ResponseType.FORWARD, PagePath.START_PAGE);
    }

    @Override
    public CommandResult exit(RequestContent requestContent) {
        if (requestContent.getSession() != null) {
            requestContent.getSession().invalidate();
        }

        requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, PagePath.START_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult create(RequestContent requestContent) throws ApplicationException {
        if (requestContent.getRequestParameter(ProjectConstant.ROLE) != null) {
            Role role = null;
            try {
                role = Role.valueOf(requestContent.getRequestParameter(ProjectConstant.ROLE).toUpperCase());
            } catch (IllegalArgumentException e) {
                log.error("no enum for " + requestContent.getRequestParameter(ProjectConstant.ROLE), e);
            }

            UserServiceFactory factory = UserServiceFactoryImpl.getInstance();

            return factory.getUserService(role).create(requestContent);
        }
        String page = requestContent.getRequestParameter("page");
        if (page!=null){
            return new CommandResult(ResponseType.FORWARD, page);
        }
        return new CommandResult(ResponseType.FORWARD, PagePath.START_PAGE);
    }

    @Override
    public CommandResult findById(RequestContent requestContent) {
        return null;
    }

    @Override
    public CommandResult update(RequestContent requestContent)throws ApplicationException {
        return null;
    }

    @Override
    public CommandResult delete(RequestContent requestContent) throws ApplicationException {
        return null;
    }
}
