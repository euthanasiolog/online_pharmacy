package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.dao.*;
import com.epam.pharmacy.dao.impl.ClientDaoImpl;
import com.epam.pharmacy.dao.impl.DoctorDaoImpl;
import com.epam.pharmacy.dao.impl.DrugDaoImpl;
import com.epam.pharmacy.dao.impl.RecipeDaoImpl;
import com.epam.pharmacy.exception.ApplicationException;
import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.model.recipe.Recipe;
import com.epam.pharmacy.model.recipe.RecipeRequest;
import com.epam.pharmacy.model.recipe.RecipeType;
import com.epam.pharmacy.model.user.Client;
import com.epam.pharmacy.model.user.Doctor;
import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.service.RecipeService;
import com.epam.pharmacy.weblayer.command.ResponseType;
import lombok.extern.log4j.Log4j2;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
public class RecipeServiceImpl implements RecipeService {
    @Override
    public CommandResult create(RequestContent requestContent) throws ApplicationException {
        Recipe recipe = createRecipeAttributesFromForm(requestContent, new Recipe());

        RecipeDao recipeDao = new RecipeDaoImpl();
        try {
            recipeDao.create(recipe, null);
        } catch (DaoException e) {
            log.error("error create recipe", e);
            throw new ApplicationException("error create recipe", e);
        }

        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult findById(RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));

        RecipeDao recipeDao = new RecipeDaoImpl();
        try {
            Recipe recipe = recipeDao.findById(id);
            requestContent.insertAttribute(ProjectConstant.RECIPE, recipe);
        } catch (DaoException e) {
            log.error("error find recipe by id", e);
            throw new ApplicationException("error find recipe by id", e);
        }

        return new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE));
    }

    @Override
    public CommandResult update(RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));
        Recipe recipe = createRecipeAttributesFromForm(requestContent, new Recipe());
        recipe.setId(id);

        RecipeDao recipeDao = new RecipeDaoImpl();
        try {
            recipeDao.update(recipe);
        } catch (DaoException e) {
            log.error("error update recipe", e);
            throw new ApplicationException("error update recipe", e);
        }

        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult delete(RequestContent requestContent) throws ApplicationException {
        Recipe recipe = new Recipe();
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));
        recipe.setId(id);

        RecipeDao recipeDao = new RecipeDaoImpl();
        try {
            recipeDao.delete(recipe);
        } catch (DaoException e) {
            log.error("error delete message", e);
            throw new ApplicationException("error delete message", e);
        }

        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult useRecipe (RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));

        RecipeDao recipeDao = new RecipeDaoImpl();
        try {
            recipeDao.useRecipe(id);
        } catch (DaoException e) {
            log.error("error use recipe", e);
            throw new ApplicationException("error use recipe", e);
        }

        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult requestToExtendRecipe(RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));

        RecipeDao recipeDao = new RecipeDaoImpl();
        try {
            recipeDao.resetDateInRecipe(id);
        } catch (DaoException e) {
            log.error("error request to extend recipe", e);
            throw new ApplicationException("error request to extend recipe", e);
        }

        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult startRecipeRequest(RequestContent requestContent) {
        int drugId = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.REQUEST_DRUG_ID));
        RecipeType recipeType = RecipeType.valueOf(requestContent.getRequestParameter(ProjectConstant.REQUEST_RECIPE_TYPE));

        RecipeRequest request = new RecipeRequest();
        request.setDrugId(drugId);
        request.setRecipeType(recipeType);

        requestContent.insertSessionAttribute(ProjectConstant.RECIPE_REQUEST, request);
        return new CommandResult(ResponseType.FORWARD, PagePath.RECIPE_REQUEST_CONFIRM);
    }

    @Override
    public CommandResult endRecipeRequest(RequestContent requestContent) throws ApplicationException {
        int clientId = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.CLINT_ID));
        int doctorId = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.DOCTOR_ID));
        int drugId = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.DRUG_ID));
        RecipeType recipeType = RecipeType.valueOf(requestContent.getRequestParameter(ProjectConstant.REQUEST_RECIPE_TYPE));

        RecipeDao recipeDao = new RecipeDaoImpl();
        List<Recipe> recipes;
        try {
            recipeDao.create(clientId, doctorId, drugId, recipeType);
            recipes = recipeDao.findClientRecipes(clientId);
        } catch (DaoException e) {
            log.error("error create recipe request", e);
            throw new ApplicationException("error create recipe request", e);
        }
        requestContent.insertSessionAttribute(ProjectConstant.RECIPES, recipes);
        requestContent.insertSessionAttribute(ProjectConstant.REDIRECT_PATH, PagePath.CABINET_CLIENT_PAGE);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult findClientRecipes (RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));

        RecipeDao recipeDao = new RecipeDaoImpl();
        try {
            List<Recipe> recipes = recipeDao.findClientRecipes(id);
            requestContent.insertSessionAttribute(ProjectConstant.RECIPES, recipes);
        } catch (DaoException e) {
            log.error("error find client recipes", e);
            throw new ApplicationException("error find client recipes", e);
        }
        return new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE));
    }

    @Override
    public CommandResult findDoctorEmptyRecipes (RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));

        RecipeDao recipeDao = new RecipeDaoImpl();
        try {
            List<Recipe> recipes = recipeDao.findDoctorEmptyRecipes(id);
            requestContent.insertSessionAttribute(ProjectConstant.RECIPES, recipes);
        } catch (DaoException e) {
            log.error("error find doctor empty recipes", e);
            throw new ApplicationException("error find doctor empty recipes", e);
        }
        return new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE));
    }

    @Override
    public CommandResult findAllDoctorRecipes (RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));

        RecipeDao recipeDao = new RecipeDaoImpl();
        try {
            List<Recipe> recipes = recipeDao.findAllDoctorRecipes(id);
            requestContent.insertSessionAttribute(ProjectConstant.RECIPES, recipes);
        } catch (DaoException e) {
            log.error("error find all doctor recipes", e);
            throw new ApplicationException("error find all doctor recipes", e);
        }
        return new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE));
    }

    private Recipe createRecipeAttributesFromForm (RequestContent requestContent, Recipe recipe) throws ApplicationException {
        int clientId = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.PATIENT_ID));
        int drugId = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.DRUG_ID));
        int doctorId = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.DOCTOR_ID));
        int serialnumber = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.SERIALNUMBER));
        String type = requestContent.getRequestParameter(ProjectConstant.TYPE);

        Date from = null;
        Date to = null;
        if (requestContent.getRequestParameter(ProjectConstant.FROM) != null && requestContent.getRequestParameter(ProjectConstant.TO) != null) {
            DateFormat dateFormat = DateFormat.getDateInstance();
            try {
                from = dateFormat.parse(requestContent.getRequestParameter(ProjectConstant.FROM));
                to = dateFormat.parse(requestContent.getRequestParameter(ProjectConstant.TO));
            } catch (ParseException e) {
                log.error("error parse Date", e);
                throw new ApplicationException("error parse Date", e);
            }
        }

        int used = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.USED));
        if (serialnumber != 0) {
            recipe.setSerialNumber(serialnumber);
        }
        recipe.setType(type);
        if (from != null) {
            recipe.setFrom(from);
        }
        if (to != null) {
            recipe.setTo(to);
        }
        if (used==1) {
            recipe.setUsed(true);
        }

        DrugDao drugDao = new DrugDaoImpl();
        ClientDao clientDao = new ClientDaoImpl();
        DoctorDao doctorDao = new DoctorDaoImpl();
        try {
            Drug drug = drugDao.findById(drugId);
            Client client = clientDao.findById(clientId);
            Doctor doctor = doctorDao.findById(doctorId);

            recipe.setClient(client);
            recipe.setDoctor(doctor);
            recipe.setDrug(drug);
        } catch (DaoException e) {
            log.error("error create recipe from form", e);
            throw new ApplicationException("error create recipe from form", e);
        }
        return recipe;
    }

}
