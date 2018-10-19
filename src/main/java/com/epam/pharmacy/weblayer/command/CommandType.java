package com.epam.pharmacy.weblayer.command;

import com.epam.pharmacy.service.impl.*;

public enum CommandType {
    START(new UserServiceImpl()::start),
    SIGNIN(new UserServiceImpl()::signIn),
    CHANGELOCALE(new ApplicationServiceImpl()::changeLocale),
    REGISTRATION(new UserServiceImpl()::registration),
    REGISTRATIONUSER(new UserServiceImpl()::create),
    REFRESH(new ApplicationServiceImpl()::refresh),
    SHOWALLDRUGS(new DrugServiceImpl()::showAllDrugs);

    private Command command;
    CommandType (Command command){
        this.command = command;
    }
    public Command getCommand(){
        return command;
    }
}
