package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.recipe.Recipe;
import com.epam.pharmacy.model.recipe.RecipeType;

import java.util.List;

public interface RecipeDao extends AbstractDao<Recipe>{

    boolean useRecipe (int id) throws DaoException;

    List<Recipe> findClientRecipes (int clientId) throws DaoException;

    List<Recipe> findDoctorEmptyRecipes (int doctorId) throws DaoException;

    List<Recipe> findAllDoctorRecipes (int doctorId) throws DaoException;

    boolean resetDateInRecipe (int id) throws DaoException;

    public boolean create (int clientId, int drugId, int doctorId, RecipeType recipeType) throws DaoException;
}
