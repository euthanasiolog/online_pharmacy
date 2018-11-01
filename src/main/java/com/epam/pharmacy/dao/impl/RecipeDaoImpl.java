package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.*;
import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.model.recipe.Recipe;
import com.epam.pharmacy.model.recipe.RecipeType;
import com.epam.pharmacy.model.user.Client;
import com.epam.pharmacy.model.user.Doctor;
import com.epam.pharmacy.util.constant.ProjectConstant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RecipeDaoImpl implements RecipeDao {

    @Override
    public boolean create(Recipe recipe, String password) throws DaoException {
        List<Object> params = setRecipeParams(recipe);

        return executeQueryUpdate(SQLQueries.CREATE_RECIPE, params);
    }

    @Override
    public boolean create (int clientId, int drugId, int doctorId, RecipeType recipeType) throws DaoException {
        List<Object> params = putParameters(clientId, drugId, doctorId, null, recipeType, null, null);

        return executeQueryUpdate(SQLQueries.CREATE_RECIPE, params);
     }

    @Override
    public Recipe findById(int id) throws DaoException {
        List<Object> params = putParameters(id);
        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.FIND_RECIPE_BY_ID, params);

        Recipe recipe = null;
        if (!resultSet.isEmpty()) {
            recipe = readRecipeParams(new Recipe(), resultSet.getResult().get(0));
        }
        return recipe;
    }

    @Override
    public boolean update(Recipe recipe) throws DaoException {
        List<Object> params = setRecipeParams(recipe);
        params.add(recipe.getId());

        return executeQueryUpdate(SQLQueries.UPDATE_RECIPE, params);
    }

    @Override
    public boolean delete(Recipe recipe) throws DaoException {
        List<Object> params = putParameters(recipe.getId());

        return executeQueryUpdate(SQLQueries.DELETE_RECIPE, params);
    }

    @Override
    public boolean useRecipe(int id) throws DaoException {
        List<Object> params = putParameters(id);

        return executeQueryUpdate(SQLQueries.USE_RECIPE, params);
    }

    @Override
    public List<Recipe> findClientRecipes(int clientId) throws DaoException {
        List<Object> params = putParameters(clientId);
        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.GET_CLIENT_RECIPES, params);
        List<Recipe> result = new ArrayList<>();

        if (!resultSet.isEmpty()) {
            for (Map<String, Object> res : resultSet.getResult()) {
                Recipe recipe = readRecipeParams(new Recipe(), res);
                result.add(recipe);
            }
        }
        return result;
    }

    @Override
    public List<Recipe> findDoctorEmptyRecipes(int doctorId) throws DaoException {
        List<Object> params = putParameters(doctorId);
        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.FIND_DOCTOR_EMPTY_RECIPES, params);
        List<Recipe> result = new ArrayList<>();

        if (!resultSet.isEmpty()) {
            for (Map<String, Object> res : resultSet.getResult()) {
                Recipe recipe = readRecipeParams(new Recipe(), res);
                result.add(recipe);
            }
        }
        return result;
    }

    @Override
    public List<Recipe> findAllDoctorRecipes(int doctorId) throws DaoException {
        List<Object> params = putParameters(doctorId);
        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.FIND_ALL_DOCTOR_RECIPES, params);
        List<Recipe> result = new ArrayList<>();

        if (!resultSet.isEmpty()) {
            for (Map<String, Object> res : resultSet.getResult()) {
                Recipe recipe = readRecipeParams(new Recipe(), res);
                result.add(recipe);
            }
        }

        return result;
    }

    @Override
    public boolean resetDateInRecipe(int id) throws DaoException {
        List<Object> params = putParameters(id);

        return executeQueryUpdate(SQLQueries.RESET_DATE_IN_RECIPE, params);
    }

    private List<Object> setRecipeParams (Recipe recipe) {
        int clientId = recipe.getClient().getId();
        int drugId = recipe.getDrug().getId();
        int doctorId = recipe.getDoctor().getId();
        int serialnumber = recipe.getSerialNumber();
        String recipeType = recipe.getRecipeType();
        Date from = null;
        if (recipe.getFrom() != null) {
            from = recipe.getFrom();
        }
        Date to = null;
        if (recipe.getTo() != null) {
            to = recipe.getTo();
        }
        int used = 0;
        if (recipe.isUsed()) {
            used = 1;
        }

        return putParameters(clientId, drugId, doctorId, serialnumber, recipeType, from, to, used);
    }

    private Recipe readRecipeParams (Recipe recipe, Map<String, Object> res) throws DaoException {
        int id = (int) res.get(ProjectConstant.ID);
        int clientId = (int) res.get(ProjectConstant.PATIENT_ID);
        int drugId = (int) res.get(ProjectConstant.DRUG_ID);
        int doctorId = (int) res.get(ProjectConstant.DOCTOR_ID);
        int serialnumber = (int) res.get(ProjectConstant.SERIALNUMBER);
        String type = (String) res.get(ProjectConstant.TYPE);

        Date from = (Date) res.get(ProjectConstant.FROM);

        Date to = (Date) res.get(ProjectConstant.TO);
        int used = (int) res.get(ProjectConstant.USED);

        recipe.setId(id);
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

        Drug drug = drugDao.findById(drugId);
        Client client = clientDao.findById(clientId);
        Doctor doctor = doctorDao.findById(doctorId);

        recipe.setClient(client);
        recipe.setDoctor(doctor);
        recipe.setDrug(drug);

        return recipe;
    }

}
