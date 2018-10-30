package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.dao.*;
import com.epam.pharmacy.dao.impl.CartDaoImpl;
import com.epam.pharmacy.dao.impl.DoctorDaoImpl;
import com.epam.pharmacy.dao.impl.RecipeDaoImpl;
import com.epam.pharmacy.model.Cart;
import com.epam.pharmacy.model.recipe.Recipe;
import com.epam.pharmacy.model.user.Doctor;
import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.util.validator.RequestValidator;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.weblayer.command.ResponseType;
import com.epam.pharmacy.service.ClientService;
import com.epam.pharmacy.dao.impl.ClientDaoImpl;
import com.epam.pharmacy.model.user.Client;
import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;
import com.epam.pharmacy.exception.ApplicationException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ClientServiceImpl extends UserServiceImpl implements ClientService {

    @Override
    public CommandResult create(RequestContent requestContent) throws ApplicationException {
        RequestValidator  validator = new RequestValidator();

        if (isEmailNotExist(requestContent) & isNickNameNotExist(requestContent)){

            Client client = new Client();
            createUserAttributes(client, requestContent);
                if (validator.isEntityValid(client, requestContent) & validator.isRegistrationPasswordsValid(requestContent)){
                    String password = hashPassword(requestContent.getRequestParameter(ProjectConstant.PASS1), log);
                    ClientDao clientDao = new ClientDaoImpl();
                    try {
                        clientDao.create(client, password);
                    } catch (DaoException e) {
                        log.error("error create client", e);
                        throw new ApplicationException("error create client", e);
                    }
                    insertUserSessionAttributes(requestContent, client, Role.CLIENT);
                    requestContent.insertSessionAttribute(ProjectConstant.CART, new Cart(client.getId()));
                    requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, PagePath.REGISTRATION_CONFIRM_PAGE);
                    return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
                }
        }
        return new CommandResult(ResponseType.FORWARD, PagePath.REGISTRATION_CLIENT_PAGE);
    }

    @Override
    public CommandResult update(RequestContent requestContent) {
        return null;
    }

    @Override
    public CommandResult delete(RequestContent requestContent) {
        return null;
    }

    @Override
    public CommandResult goToCabinet(RequestContent requestContent) {
        return new CommandResult(ResponseType.FORWARD, PagePath.CABINET_CLIENT_PAGE);
    }

    @Override
    public CommandResult endSignIn(User user, RequestContent requestContent, Role role) throws ApplicationException {
        CartDao cartDao = new CartDaoImpl();
        try {
            Cart cart = cartDao.findById(user.getId());
            requestContent.insertSessionAttribute(ProjectConstant.CART, cart);

            DoctorDao doctorDao = new DoctorDaoImpl();
            List<Doctor> doctors = doctorDao.getDoctors();
            requestContent.insertSessionAttribute(ProjectConstant.DOCTORS, doctors);

            RecipeDao recipeDao = new RecipeDaoImpl();
            List<Recipe> recipes = recipeDao.findClientRecipes(user.getId());
            requestContent.insertSessionAttribute(ProjectConstant.RECIPES, recipes);
        } catch (DaoException e) {
            log.error("error get client session attributes", e);
            throw new ApplicationException("error get client session attributes", e);
        }

        requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, PagePath.CABINET_CLIENT_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

}
