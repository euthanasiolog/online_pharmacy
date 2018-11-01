package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.util.validator.RequestValidator;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.weblayer.command.ResponseType;
import com.epam.pharmacy.service.PharmacistService;
import com.epam.pharmacy.dao.PharmacistDao;
import com.epam.pharmacy.dao.impl.PharmacistDaoImpl;
import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;
import com.epam.pharmacy.exception.ApplicationException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PharmacistServiceImpl extends UserServiceImpl implements PharmacistService {
    @Override
    public CommandResult create(RequestContent requestContent) throws ApplicationException {
        RequestValidator validator = new RequestValidator();

        if (isNickNameNotExist(requestContent) & isEmailNotExist(requestContent)){

            String password = hashPassword(requestContent.getRequestParameter(ProjectConstant.PASS1), log);
            User user = new User();

            createUserAttributes(user, requestContent);
                if (validator.isEntityValid(user, requestContent) & validator.isRegistrationPasswordsValid(requestContent)){
                    PharmacistDao pharmacistDao = new PharmacistDaoImpl();
                    try {
                        pharmacistDao.create(user, password);
                    } catch (DaoException e) {
                        log.error("error create pharmacist", e);
                        throw new ApplicationException("error create pharmacist", e);
                    }
                    insertUserSessionAttributes(requestContent, user, Role.PHARMACIST);

                    requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, PagePath.REGISTRATION_SUCCESSFUL_PAGE);
                    return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
                }
        }
        return new CommandResult(ResponseType.FORWARD, PagePath.REGISTRATION_PHARMACIST_PAGE);
    }

    @Override
    public CommandResult update(RequestContent requestContent) throws ApplicationException {
        return super.update(requestContent);
    }

    @Override
    public CommandResult delete(RequestContent requestContent) throws ApplicationException {
        return super.delete(requestContent);
    }

    @Override
    public CommandResult goToCabinet(RequestContent requestContent) {
        return new CommandResult(ResponseType.FORWARD, PagePath.CABINET_PHARMACIST_PAGE);
    }

    @Override
    public CommandResult endSignIn(User user, RequestContent requestContent, Role role) {
        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, PagePath.CABINET_PHARMACIST_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }
}
