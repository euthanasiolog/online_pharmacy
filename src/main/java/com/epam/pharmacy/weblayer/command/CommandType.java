package com.epam.pharmacy.weblayer.command;

import com.epam.pharmacy.service.impl.*;

public enum CommandType {
    START(new UserServiceImpl()::start),
    SIGN_IN(new UserServiceImpl()::signIn),
    CHANGE_LOCALE(new ApplicationServiceImpl()::changeLocale),
    REGISTRATION(new UserServiceImpl()::registration),
    REGISTRATION_USER(new UserServiceImpl()::create),
    REFRESH(new ApplicationServiceImpl()::refresh),
    SAVE_CART(new CartServiceImpl()::create),
    PUT_DRUG_IN_CART(new CartServiceImpl()::putDrugInCart),
    PAYMENT(new CartServiceImpl()::payment),
    DELETE_FROM_CART(new CartServiceImpl()::delete),
    GET_CLIENT_CARD(new CartServiceImpl()::findById),
    SHOW_ALL_DRUGS(new DrugServiceImpl()::showAllDrugs);

    private Command command;
    CommandType (Command command){
        this.command = command;
    }
    public Command getCommand(){
        return command;
    }
}
