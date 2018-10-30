package com.epam.pharmacy.weblayer.command;

import com.epam.pharmacy.service.impl.*;
import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;

public enum CommandType {
    START(requestContent -> new CommandResult(ResponseType.FORWARD, PagePath.START_PAGE)),
    SIGN_IN(new UserServiceImpl()::signIn),
    CHANGE_LOCALE(new ApplicationServiceImpl()::changeLocale),
    REGISTRATION(new UserServiceImpl()::registration),
    REGISTRATION_USER(new UserServiceImpl()::create),
    REFRESH(requestContent -> new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE))),
    SAVE_CART(new CartServiceImpl()::create),
    PUT_DRUG_IN_CART(new CartServiceImpl()::putDrugInCart),
    PAYMENT(new CartServiceImpl()::payment),
    DELETE_FROM_CART(new CartServiceImpl()::delete),
    GET_CLIENT_CARD(new CartServiceImpl()::findById),
    SEARCH_DRUG(new DrugServiceImpl()::searchDrug),
    GO_TO_START(requestContent -> new CommandResult(ResponseType.FORWARD, PagePath.START_PAGE)),
    GO_TO_CABINET(new UserServiceImpl()::goToCabinet),
    GO_TO_ADD_DRUG(requestContent -> new CommandResult(ResponseType.FORWARD, PagePath.ADD_DRUG)),
    GO_TO_MANAGE_DRUG(requestContent -> new CommandResult(ResponseType.FORWARD, PagePath.MANAGE_DRUG)),
    EXIT(new UserServiceImpl()::exit),
    DO_PAYMENT(new CartServiceImpl()::doPayment),
    ADD_DRUG_PAGE(requestContent -> new CommandResult(ResponseType.FORWARD, PagePath.ADD_DRUG)),
    ADD_DRUG(new DrugServiceImpl()::create),
    REQUEST_TO_EXTEND_RECIPE(new RecipeServiceImpl()::requestToExtendRecipe),
    DELETE_RECIPE(new RecipeServiceImpl()::delete),
    CONFIRM_USER_QUERY(new AdminServiceImpl()::confirmUserRequest),
    DELETE_USER(new AdminServiceImpl()::deleteUser),
    SHOW_ALL_DRUGS(new DrugServiceImpl()::showAllDrugs);

    private Command command;
    CommandType (Command command){
        this.command = command;
    }
    public Command getCommand(){
        return command;
    }
}
