package com.epam.pharmacy.service;

import com.epam.pharmacy.exception.ApplicationException;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;

public interface RecipeService extends AbstractService{

    CommandResult useRecipe (RequestContent requestContent) throws ApplicationException;

    CommandResult findClientRecipes (RequestContent requestContent) throws ApplicationException;

    CommandResult findDoctorEmptyRecipes (RequestContent requestContent) throws ApplicationException;

    CommandResult findAllDoctorRecipes (RequestContent requestContent) throws ApplicationException;

    CommandResult requestToExtendRecipe (RequestContent requestContent) throws ApplicationException;

    CommandResult startRecipeRequest (RequestContent requestContent);

    CommandResult endRecipeRequest (RequestContent requestContent) throws ApplicationException;

}
