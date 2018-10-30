package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.RecipeDao;
import com.epam.pharmacy.dao.impl.RecipeDaoImpl;
import com.epam.pharmacy.model.recipe.Recipe;
import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.util.validator.RequestValidator;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.weblayer.command.ResponseType;
import com.epam.pharmacy.service.DoctorService;
import com.epam.pharmacy.dao.DoctorDao;
import com.epam.pharmacy.dao.impl.DoctorDaoImpl;
import com.epam.pharmacy.model.user.Doctor;
import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;
import com.epam.pharmacy.exception.ApplicationException;
import lombok.extern.log4j.Log4j2;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Log4j2
public class DoctorServiceImpl extends UserServiceImpl implements DoctorService {

    @Override
    public CommandResult create(RequestContent requestContent) throws ApplicationException {
        RequestValidator validator = new RequestValidator();

        if (isNickNameNotExist(requestContent) & isEmailNotExist(requestContent)){

            String password = hashPassword(requestContent.getRequestParameter(ProjectConstant.PASS1), log);

            Doctor doctor = new Doctor();
            createUserAttributes(doctor, requestContent);
                String specialization = requestContent.getRequestParameter("specialization");
                String workplace = requestContent.getRequestParameter("workplace");

                doctor.setSpecialization(specialization);
                doctor.setWorkplace(workplace);

                if (validator.isEntityValid(doctor, requestContent) & validator.isRegistrationPasswordsValid(requestContent)){
                    DoctorDao doctorDao = new DoctorDaoImpl();
                    try {
                        doctorDao.create(doctor, password);
                    } catch (DaoException e) {
                        log.error("error create doctor", e);
                        throw new ApplicationException("error create doctor", e);
                    }
                    insertUserSessionAttributes(requestContent, doctor, Role.CLIENT);
                    requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, PagePath.REGISTRATION_SUCCESSFUL_PAGE);
                    return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
                }
        }
        return new CommandResult(ResponseType.FORWARD, PagePath.REGISTRATION_DOCTOR_PAGE);
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
        return new CommandResult(ResponseType.FORWARD, PagePath.CABINET_DOCTOR_PAGE);
    }

    @Override
    public CommandResult endSignIn(User user, RequestContent requestContent, Role role) throws ApplicationException {
        RecipeDao recipeDao = new RecipeDaoImpl();

        List<Recipe> recipeRequests;
        try {
            recipeRequests = recipeDao.findDoctorEmptyRecipes(user.getId());
        } catch (DaoException e) {
            log.error("error get doctor empty recipes", e);
            throw new ApplicationException("error get doctor empty recipes", e);
        }

        requestContent.insertSessionAttribute(ProjectConstant.RECIPE_REQUEST, recipeRequests);
        requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, PagePath.START_DOCTOR_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult confirmRecipe(RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));
        Date fromDate = null;
        Date toDate = null;
        if (requestContent.getRequestParameter(ProjectConstant.FROM) != null && requestContent.getRequestParameter(ProjectConstant.TO) != null) {
            DateFormat dateFormat = DateFormat.getDateInstance();
            try {
                fromDate = dateFormat.parse(requestContent.getRequestParameter(ProjectConstant.FROM));
                toDate = dateFormat.parse(requestContent.getRequestParameter(ProjectConstant.TO));
            } catch (ParseException e) {
                log.error("error parse Date when confirm recipe", e);
                throw new ApplicationException("error parse Date when confirm recipe", e);
            }
        }

        RecipeDao recipeDao = new RecipeDaoImpl();
        if (fromDate != null && toDate != null) {
            Recipe recipe;
            try {
                recipe = recipeDao.findById(id);
            } catch (DaoException e) {
                log.error("error find recipe", e);
                throw new ApplicationException("error find recipe", e);
            }
            if (recipe != null) {
                recipe.setFrom(fromDate);
                recipe.setTo(toDate);
                try {
                    recipeDao.update(recipe);
                } catch (DaoException e) {
                    log.error("error update recipe", e);
                    throw new ApplicationException("error update recipe", e);
                }
            }
        }
        requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }
}
